package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomerState;
import de.schulung.spring.customers.domain.CustomersSink;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JpaCustomersSink
  implements CustomersSink {

  private final CustomerEntityRepository repo;
  private final CustomerEntityMapper mapper;

  @Override
  public Stream<Customer> findAll() {
    return repo
      .findAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findAllByState(CustomerState state) {
    return repo
      .findAllByState(state)
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Customer> findById(UUID uuid) {
    return repo
      .findById(uuid)
      .map(mapper::map);
  }

  @Override
  public long count() {
    return repo
      .count();
  }

  @Override
  public void create(Customer customer) {
    var entity = mapper.map(customer);
    repo
      .save(entity);
    // customer.setUuid(entity.getUuid());
    mapper.copy(entity, customer);
  }

  @Override
  public boolean update(Customer customer) {
    if (!repo.existsById(customer.getUuid())) {
      return false;
    }
    var entity = mapper.map(customer);
    repo.save(entity);
    mapper.copy(entity, customer);
    return true;
  }

  @Override
  public boolean delete(UUID uuid) {
    if (!repo.existsById(uuid)) {
      return false;
    }
    repo
      .deleteById(uuid);
    return true;
  }
}
