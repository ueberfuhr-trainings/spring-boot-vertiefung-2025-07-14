package de.schulung.spring.customers.persistence.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "Address")
@Table(name = "ADDRESSES")
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;

  private String street;
  private String number;
  private String zip;
  private String city;

  @OneToOne(mappedBy = "address")
  private CustomerEntity customer;

}
