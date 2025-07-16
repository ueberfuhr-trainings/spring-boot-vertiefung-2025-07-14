package de.schulung.spring.customers.shared.interceptors;

import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

@RequiredArgsConstructor
public class MethodInterceptorPostProcessor
  extends AbstractBeanFactoryAwareAdvisingPostProcessor
  implements InitializingBean {

  private final Pointcut pointcut;
  private final Advice advice;

  @Override
  public void afterPropertiesSet() {
    this.advisor = new DefaultPointcutAdvisor(
      pointcut, // where?
      advice // what? (interceptor)
    );
  }

}
