package de.schulung.spring.customers.persistence.inmemory;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomersSink;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class InMemoryCustomersSink
  implements CustomersSink {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @Override
  public Stream<Customer> findAll() {
    return customers
      .values()
      .stream();
  }

  @Override
  public long count() {
    return customers.size();
  }

  @Override
  public void create(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  @Override
  public boolean delete(UUID uuid) {
    return customers.remove(uuid) != null;
  }
}
