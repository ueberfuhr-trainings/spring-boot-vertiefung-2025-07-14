package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomerFetchOptions;
import de.schulung.spring.customers.domain.CustomerState;
import de.schulung.spring.customers.domain.CustomersSink;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JpaCustomersSink
  implements CustomersSink {

  private final CustomerEntityRepository repo;
  private final CustomerEntityMapper mapper;
  private final EntityManager em;

  @Override
  public Stream<Customer> findAll(CustomerFetchOptions fetchOptions) {
    var entityGraph = em
      .createEntityGraph(CustomerEntity.class);
    if (fetchOptions.isEnableAddress()) {
      entityGraph.addSubgraph("address");
    }
    return em
      .createQuery("select c from Customer c", CustomerEntity.class)
      .setHint("jakarta.persistence.fetchgraph", entityGraph)
      .getResultList()

      // return repo
      //  .findAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findAllByState(CustomerState state, CustomerFetchOptions fetchOptions) {

    var entityGraph = em
      .createEntityGraph(CustomerEntity.class);
    if (fetchOptions.isEnableAddress()) {
      entityGraph.addSubgraph("address");
    }
    return em
      .createQuery("select c from Customer c where state = :state", CustomerEntity.class)
      .setParameter("state", state)
      .setHint("jakarta.persistence.fetchgraph", entityGraph)
      .getResultList()
      // return repo
      //   .findAllByState(state)
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
    var existingCustomerResult = repo.findById(customer.getUuid());
    if (existingCustomerResult.isEmpty()) {
      return false;
    }
    var existingCustomer = existingCustomerResult.get();
    mapper.copy(customer, existingCustomer);
    repo.save(existingCustomer);
    mapper.copy(existingCustomer, customer);
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
