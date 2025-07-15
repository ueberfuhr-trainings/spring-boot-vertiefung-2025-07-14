package de.schulung.spring.customers.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.schulung.spring.customers.shared.validation.Adult;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CustomerDto {

  @JsonProperty(
    access = JsonProperty.Access.READ_ONLY
  )
  private UUID uuid;
  @NotNull
  private String name;
  @NotNull
  @Adult
  private LocalDate birthdate;
  @CustomerStateParam
  private String state = "active";

}
