package de.schulung.spring.customers.domain;

import de.schulung.spring.customers.domain.events.CustomerCreatedEvent;
import de.schulung.spring.customers.domain.events.CustomerDeletedEvent;
import de.schulung.spring.customers.domain.events.CustomerUpdatedEvent;
import de.schulung.spring.customers.shared.interceptors.LogPerformance;
import de.schulung.spring.customers.shared.interceptors.PublishEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersSink sink;

  public long count() {
    return sink.count();
  }

  public Stream<Customer> findAll() {
    return this.findAll(CustomerFetchOptions.DEFAULT);
  }

  public Stream<Customer> findAll(CustomerFetchOptions fetchOptions) {
    return sink.findAll(fetchOptions);
  }

  public Stream<Customer> findAllByState(CustomerState state) {
    return this.findAllByState(state, CustomerFetchOptions.DEFAULT);
  }

  public Stream<Customer> findAllByState(CustomerState state, CustomerFetchOptions fetchOptions) {
    return sink.findAllByState(state, fetchOptions);
  }

  public Optional<Customer> findById(UUID uuid) {
    return sink.findById(uuid);
  }

  @LogPerformance
  @PublishEvent(CustomerCreatedEvent.class)
  public void create(@Valid Customer customer) {
    sink.create(customer);
  }

  @PublishEvent(CustomerUpdatedEvent.class)
  public boolean update(@Valid Customer customer) {
    return sink.update(customer);
  }

  @PublishEvent(CustomerDeletedEvent.class)
  public boolean delete(UUID uuid) {
    return sink.delete(uuid);
  }

}
