package de.schulung.spring.customers.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DomainTest(initializationEnabled = true)
class CustomerInitializationWithEmptyCustomersTests {

  @Autowired
  CustomersService customersService;


  @Test
  void shouldInitializeCustomer() {
    assertThat(this.customersService.count())
      .isGreaterThan(0);
  }

}
