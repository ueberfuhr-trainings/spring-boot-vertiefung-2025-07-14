package de.schulung.spring.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CustomersServiceTests {

  @Autowired
  CustomersService customersService;

  // wenn Customer erzeugt wird -> UUID
  @Test
  void shouldAssignUuidWhenCustomerCreated() {
    var customer = new Customer();
    customer.setName("Tom Mayer");
    customer.setBirthdate(LocalDate.of(1995, Month.AUGUST, 8));

    customersService.create(customer);

    assertThat(customer.getUuid())
      .isNotNull();
  }

  // Validierung: Customer ohne Name anlegen -> Exception
  @Test
  void shouldNotCreateCustomerWithoutName() {
    var customer = new Customer();
    customer.setBirthdate(LocalDate.of(1995, Month.AUGUST, 8));

    assertThatThrownBy(() -> customersService.create(customer))
      .isNotNull();
  }

}
