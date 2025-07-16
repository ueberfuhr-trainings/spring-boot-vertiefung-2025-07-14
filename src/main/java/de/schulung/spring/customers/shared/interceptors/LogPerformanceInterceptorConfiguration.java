package de.schulung.spring.customers.shared.interceptors;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogPerformanceInterceptorConfiguration {

  @Bean
  MethodInterceptorPostProcessor logPerformanceInterceptorPostProcessorOnMethodLevel(
    LogPerformanceInterceptor logPerformanceInterceptor
  ) {
    return new MethodInterceptorPostProcessor(
      new AnnotationMatchingPointcut(
        null, // class-level
        LogPerformance.class, // method-level
        true
      ),
      logPerformanceInterceptor
    );
  }

  @Bean
  MethodInterceptorPostProcessor logPerformanceInterceptorPostProcessorOnClassLevel(
    LogPerformanceInterceptor logPerformanceInterceptor
  ) {
    return new MethodInterceptorPostProcessor(
      new AnnotationMatchingPointcut(
        LogPerformance.class, // class-level
        null, // method-level
        true
      ),
      logPerformanceInterceptor
    );
  }

}
