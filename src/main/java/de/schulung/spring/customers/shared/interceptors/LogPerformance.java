package de.schulung.spring.customers.shared.interceptors;

import org.slf4j.event.Level;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {
  ElementType.METHOD,
  ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogPerformance {

  Level value() default Level.INFO;

}
