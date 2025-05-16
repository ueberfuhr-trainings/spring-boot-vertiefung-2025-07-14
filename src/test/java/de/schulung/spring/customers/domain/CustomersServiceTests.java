package de.schulung.spring.customers.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
class CustomersServiceTests {

  @Autowired
  CustomersService customersService;

  // wenn Customer erzeugt wird -> UUID
  @Test
  void shouldAssignUuidWhenCustomerCreated() {
    var customer = Customer
      .builder()
      .name("Tom Mayer")
      .birthdate(LocalDate.of(1995, Month.AUGUST, 8))
      .build();

    customersService.create(customer);

    assertThat(customer.getUuid())
      .isNotNull();
  }

  // Validierung: Customer ohne Name anlegen -> Exception
  @Test
  void shouldNotCreateCustomerWithoutName() {
    var customer = Customer
      .builder()
      .birthdate(LocalDate.of(1995, Month.AUGUST, 8))
      .build();

    assertThatThrownBy(() -> customersService.create(customer))
      .isNotNull();
  }

}
