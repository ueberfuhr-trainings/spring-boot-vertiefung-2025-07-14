package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.CustomerState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Customer")
@Table(name = "CUSTOMERS")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  private String name;
  @Column(name = "BIRTH_DATE")
  private LocalDate birthdate;
  private CustomerState state;


}
