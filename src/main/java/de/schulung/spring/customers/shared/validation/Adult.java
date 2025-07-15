package de.schulung.spring.customers.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Bean Validation
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {

  long value() default 18;

  ChronoUnit unit() default ChronoUnit.YEARS;

  String message() default "should be an adult";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
