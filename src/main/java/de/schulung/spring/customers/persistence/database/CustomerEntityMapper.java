package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(
  componentModel = "spring",
  uses = {
    AddressEntityMapper.class,
  }
)
public interface CustomerEntityMapper {

  CustomerEntity map(Customer source);

  Customer map(CustomerEntity source);

  void copy(Customer source, @MappingTarget CustomerEntity entity);

  void copy(CustomerEntity source, @MappingTarget Customer target);

  @AfterMapping
  default void linkAddress(
    @MappingTarget CustomerEntity entity
  ) {
    Optional
      .ofNullable(entity.getAddress())
      .ifPresent(address -> address.setCustomer(entity));
  }
}
