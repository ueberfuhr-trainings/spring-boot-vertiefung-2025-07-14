package de.schulung.spring.customers.persistence.inmemory;

import de.schulung.spring.customers.domain.CustomersSink;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryCustomersSinkConfiguration {

  @ConditionalOnMissingBean
  @Bean
  CustomersSink inMemoryCustomersSink() {
    return new InMemoryCustomersSink();
  }

}
