package de.schulung.spring.customers.boundary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

  @NotNull
  @Size(min = 3, max = 100)
  private String street;
  @Size(max = 100)
  private String number;
  @NotNull
  @Pattern(regexp = "[0-9]{5}")
  private String zip;
  @NotNull
  @Size(min = 1, max = 100)
  private String city;
}
