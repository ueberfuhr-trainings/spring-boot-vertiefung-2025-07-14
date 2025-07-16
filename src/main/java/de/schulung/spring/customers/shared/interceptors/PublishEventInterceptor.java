package de.schulung.spring.customers.shared.interceptors;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublishEventInterceptor
  implements MethodInterceptor {

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    // create event object
    Object event = null;
    final var annotation = AnnotationUtils
      .findAnnotation(invocation.getMethod(), PublishEvent.class);
    if (null != annotation) {
      event = annotation.value()
        .getConstructor(invocation.getMethod().getParameterTypes())
        .newInstance(invocation.getArguments());
    }
    // invoke method
    final var result = invocation.proceed();
    // if successful, fire event
    if (null != event) {
      eventPublisher.publishEvent(event);
    }
    return result;
  }

}
