package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.CustomerState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerStateConverter
  implements AttributeConverter<CustomerState, String> {

  @Override
  public String convertToDatabaseColumn(CustomerState attribute) {
    return null == attribute ? null : switch (attribute) {
      case ACTIVE -> "a";
      case LOCKED -> "l";
      case DISABLED -> "d";
    };
  }

  @Override
  public CustomerState convertToEntityAttribute(String dbData) {
    return null == dbData ? null : switch (dbData) {
      case "a" -> CustomerState.ACTIVE;
      case "l" -> CustomerState.LOCKED;
      case "d" -> CustomerState.DISABLED;
      default -> throw new IllegalStateException("Unexpected value: " + dbData);
    };
  }
}
