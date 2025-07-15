package de.schulung.spring.customers.infrastructure;

import de.schulung.spring.customers.domain.events.CustomerCreatedEvent;
import de.schulung.spring.customers.domain.events.CustomerDeletedEvent;
import de.schulung.spring.customers.domain.events.CustomerUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventsLogger {

  @Async
  @EventListener
  void logCustomerCreated(CustomerCreatedEvent event) {
    log.info("Customer created with id {}", event.customer().getUuid());
  }

  @Async
  @EventListener
  void logCustomerUpdated(CustomerUpdatedEvent event) {
    log.info("Customer updated with id {}", event.customer().getUuid());
  }

  @Async
  @EventListener
  void logCustomerDeleted(CustomerDeletedEvent event) {
    log.info("Customer deleted with id {}", event.uuid());
  }

}
