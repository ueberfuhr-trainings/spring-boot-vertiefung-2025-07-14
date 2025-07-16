package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomersService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UuidToCustomerConverter implements Converter<String, Customer> {

  private static final Pattern UUID_REGEX =
    Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$");

  private final CustomersService customersService;

  @Override
  public Customer convert(
    @SuppressWarnings("NullableProblems")
    String source
  ) {
    if (!UUID_REGEX.matcher(source).matches()) {
      throw new ValidationException("Invalid UUID");
    }
    return customersService
      .findById(UUID.fromString(source))
      .orElseThrow(NotFoundException::new);
  }
}
