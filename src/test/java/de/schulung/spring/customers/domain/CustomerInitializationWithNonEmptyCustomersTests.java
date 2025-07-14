package de.schulung.spring.customers.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DomainTest(initializationEnabled = true)
// test configuration will lead to a new context, so we destroy the context after this
// test class execution
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CustomerInitializationWithNonEmptyCustomersTests {

  @Autowired
  CustomersService customersService;

  static final Customer sampleCustomer = Customer
    .builder()
    .name("Tom")
    .birthdate(LocalDate.of(1980, Month.APRIL, 1))
    .build();

  @TestConfiguration
  static class MockConfiguration {
    @Bean
    public BeanPostProcessor customersServiceInitializerBeforeInjectionPostProcessor() {
      //noinspection NullableProblems
      return new BeanPostProcessor() {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
          if (bean instanceof CustomersService service) {
            service.create(sampleCustomer);
          }
          return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
      };
    }
  }

  @Test
  void shouldInitializeCustomer() {
    assertThat(this.customersService.findAll())
      .hasSize(1)
      .first()
      .usingRecursiveComparison()
      .isEqualTo(sampleCustomer);

  }

}
