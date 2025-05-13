package de.schulung.spring.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Customer {

  @JsonProperty(
    access = JsonProperty.Access.READ_ONLY
  )
  private UUID uuid;
  @NotNull
  private String name;
  @NotNull
  private LocalDate birthdate;
  @Pattern(regexp = "active|locked|disabled")
  private String state = "active";


}
