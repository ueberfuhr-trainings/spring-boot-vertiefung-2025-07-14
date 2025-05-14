package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomerState;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CustomerDtoMapper {

  Customer map(CustomerDto source) {
    if (null == source) {
      return null;
    }
    return Customer
      .builder()
      .name(source.getName())
      .birthdate(source.getBirthdate())
      .state(mapState(source.getState()))
      .build();
  }

  CustomerDto map(Customer source) {
    if (null == source) {
      return null;
    }
    var result = new CustomerDto();
    result.setUuid(source.getUuid());
    result.setName(source.getName());
    result.setBirthdate(source.getBirthdate());
    result.setState(mapState(source.getState()));
    return result;
  }

  String mapState(CustomerState state) {
    return null == state ? null : switch (state) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  CustomerState mapState(String state) {
    return null == state ? null : switch (state) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    };
  }

}
