package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressEntityMapper {

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "customer", ignore = true)
  AddressEntity map(Address address);

  Address map(AddressEntity source);

  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "customer", ignore = true)
  void copy(Address source, @MappingTarget AddressEntity entity);

  void copy(AddressEntity source, @MappingTarget Address target);

}
