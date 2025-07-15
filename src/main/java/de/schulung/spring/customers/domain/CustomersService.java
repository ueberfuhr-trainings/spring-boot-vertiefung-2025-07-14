package de.schulung.spring.customers.domain;

import de.schulung.spring.customers.domain.events.CustomerCreatedEvent;
import de.schulung.spring.customers.domain.events.CustomerDeletedEvent;
import de.schulung.spring.customers.domain.events.CustomerUpdatedEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
//@Slf4j
public class CustomersService {

  private final CustomersSink sink;
  private final ApplicationEventPublisher publisher;

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
    publisher.publishEvent(new CustomerCreatedEvent(customer));
  }

  public boolean update(@Valid Customer customer) {
    var result = sink.update(customer);
    publisher.publishEvent(new CustomerUpdatedEvent(customer));
    return result;
  }

  public boolean delete(UUID uuid) {
    var result = sink.delete(uuid);
    publisher.publishEvent(new CustomerDeletedEvent(uuid));
    return result;
  }

}
