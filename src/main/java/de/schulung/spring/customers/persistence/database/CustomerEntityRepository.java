package de.schulung.spring.customers.persistence.database;


import de.schulung.spring.customers.domain.CustomerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerEntityRepository
  extends JpaRepository<CustomerEntity, UUID> {

  List<CustomerEntity> findAllByState(CustomerState state);

  // @Query("SELECT c FROM Customer c WHERE c.state = :state")
  // List<CustomerEntity> gelbeKatze(@Param("state") CustomerState state);

}
