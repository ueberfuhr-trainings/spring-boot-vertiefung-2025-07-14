package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDtoMapper {

  AddressDto map(Address source);

  Address map(AddressDto source);

}
