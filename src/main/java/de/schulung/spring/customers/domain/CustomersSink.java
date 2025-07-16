package de.schulung.spring.customers.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll(CustomerFetchOptions fetchOptions);

  default Stream<Customer> findAllByState(CustomerState state, CustomerFetchOptions fetchOptions) {
    return findAll(fetchOptions)
      .filter(c -> c.getState() == state);
  }

  default Optional<Customer> findById(UUID uuid) {
    return findAll(CustomerFetchOptions.DEFAULT)
      .filter(c -> c.getUuid().equals(uuid))
      .findFirst();
  }

  default long count() {
    return findAll(CustomerFetchOptions.DEFAULT)
      .count();
  }

  void create(Customer customer);

  default boolean update(Customer customer) {
    var result = findById(customer.getUuid());
    if (result.isEmpty()) {
      return false;
    }
    var oldCustomer = result.get();
    oldCustomer.setState(customer.getState());
    oldCustomer.setName(customer.getName());
    oldCustomer.setBirthdate(customer.getBirthdate());
    return true;
  }

  boolean delete(UUID uuid);

}
