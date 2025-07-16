package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.CustomersSink;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @ConditionalOnProperty... - Schalter zum Deaktivieren
@Configuration
public class JpaCustomersSinkConfiguration {

  @Bean
  CustomersSink jpaCustomersSink(
    CustomerEntityRepository repo,
    CustomerEntityMapper mapper,
    EntityManager em
  ) {
    return new JpaCustomersSink(
      repo,
      mapper,
      em
    );
  }

}
