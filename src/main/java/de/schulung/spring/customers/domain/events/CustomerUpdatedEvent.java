package de.schulung.spring.customers.domain.events;

import de.schulung.spring.customers.domain.Customer;

public record CustomerUpdatedEvent(
  Customer customer
) {
}
