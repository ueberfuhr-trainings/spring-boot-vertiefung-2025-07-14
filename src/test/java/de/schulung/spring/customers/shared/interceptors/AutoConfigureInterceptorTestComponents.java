package de.schulung.spring.customers.shared.interceptors;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Import({
  LogPerformanceTestService.class,
  LogPerformanceTestAtClassLevelService.class,
  PublishEventTestService.class,
})
public @interface AutoConfigureInterceptorTestComponents {
}
