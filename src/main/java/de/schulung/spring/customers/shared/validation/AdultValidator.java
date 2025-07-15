package de.schulung.spring.customers.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AdultValidator
  implements ConstraintValidator<Adult, LocalDate> {

  private Adult adultAnnotation;

  @Override
  public void initialize(Adult constraintAnnotation) {
    this.adultAnnotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return null == value ||
      LocalDate
        .now()
        .minus(adultAnnotation.value(), adultAnnotation.unit())
        .plusDays(1)
        .isAfter(value);
  }
}
