package de.schulung.spring.customers.domain;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Validated
@Service
public class CustomersService {

  // TODO replace
  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  public Stream<Customer> findAll() {
    return customers
      .values()
      .stream();
  }

  public Optional<Customer> findById(UUID uuid) {
    return Optional
      .ofNullable(customers.get(uuid));
  }

  public void create(@Valid Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  public boolean delete(UUID uuid) {
    return customers.remove(uuid) != null;
  }

}
