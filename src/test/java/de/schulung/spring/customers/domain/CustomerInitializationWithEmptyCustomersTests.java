package de.schulung.spring.customers.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockReset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(
  properties = {
    "application.initialization.enabled=true",
  }
)
@Import(CustomerInitializationWithEmptyCustomersTests.MockConfiguration.class)
class CustomerInitializationWithEmptyCustomersTests {

  @Autowired
  CustomersService customersService;

  @TestConfiguration
  static class MockConfiguration {
    @Bean
    public CustomersService customersService() {
      final var result = mock(
        CustomersService.class,
        MockReset.withSettings(MockReset.AFTER)
      );
      when(result.count())
        .thenReturn(0L);
      return result;
    }
  }

  @Test
  void shouldInitializeCustomer() {
    verify(customersService)
      .count();
    verify(customersService, atLeastOnce())
      .create(any());
  }

}
