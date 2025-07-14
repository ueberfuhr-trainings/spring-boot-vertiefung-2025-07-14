package de.schulung.spring.customers.domain;

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
    return sink.findAll();
  }

  public Stream<Customer> findAllByState(CustomerState state) {
    return sink.findAllByState(state);
  }

  public Optional<Customer> findById(UUID uuid) {
    return sink.findById(uuid);
  }

  public void create(@Valid Customer customer) {
    sink.create(customer);
  }

  public boolean update(@Valid Customer customer) {
    return sink.update(customer);
  }

  public boolean delete(UUID uuid) {
    return sink.delete(uuid);
  }

}
