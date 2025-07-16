package de.schulung.spring.customers.shared.interceptors;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublishEventInterceptorConfiguration {

  @Bean
  MethodInterceptorPostProcessor publishEventInterceptorPostProcessor(
    PublishEventInterceptor publishEventInterceptor
  ) {
    return new MethodInterceptorPostProcessor(
      new AnnotationMatchingPointcut(
        null,
        PublishEvent.class,
        true
      ),
      publishEventInterceptor
    );
  }

}
