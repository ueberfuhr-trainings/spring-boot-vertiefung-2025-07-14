package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomerState;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

  Customer map(CustomerDto source);

  CustomerDto map(Customer source);

  default String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  default CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    };
  }

}
