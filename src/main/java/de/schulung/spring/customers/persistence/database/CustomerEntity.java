package de.schulung.spring.customers.persistence.database;

import de.schulung.spring.customers.domain.CustomerState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
  // @Enumerated(EnumType.STRING)
  private CustomerState state;
  @OneToOne(
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @JoinColumn(name = "address_uuid")
  private AddressEntity address;
}
