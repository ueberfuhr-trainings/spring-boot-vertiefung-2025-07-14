package de.schulung.spring.customers.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll();

  default Stream<Customer> findAllByState(CustomerState state) {
    return findAll()
      .filter(c -> c.getState() == state);
  }

  default Optional<Customer> findById(UUID uuid) {
    return findAll()
      .filter(c -> c.getUuid().equals(uuid))
      .findFirst();
  }

  default long count() {
    return findAll()
      .count();
  }

  void create(Customer customer);

  boolean delete(UUID uuid);

}
