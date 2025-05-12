package de.schulung.spring.customers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


@RestController
@RequestMapping("/customers")
class CustomersController {

  // TODO replace
  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Stream<Customer> getCustomers() {
    return customers
      .values()
      .stream();
  }

  @PostMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<Customer> createCustomer(
    @RequestBody
    Customer customer
  ) {
    if (!
      Arrays
        .asList("active", "locked", "disabled")
        .contains(customer.getState())
      || null == customer.getBirthdate()
      || null == customer.getName()
      || null != customer.getUuid()
    ) {
      return ResponseEntity
        .badRequest()
        .build();
    }
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
    var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{uuid}")
      .buildAndExpand(customer.getUuid())
      .toUri();
    return ResponseEntity
      .created(location)
      .body(customer);
  }

  @GetMapping("/{uuid}")
  ResponseEntity<Customer> findCustomerById(
    @PathVariable("uuid")
    UUID uuid
  ) {
    final var result = customers.get(uuid);
    if (null == result) {
      return ResponseEntity
        .notFound()
        .build();
    }
    return ResponseEntity
      .ok()
      .body(result);
  }

  @DeleteMapping("/{uuid}")
  ResponseEntity<Void> deleteCustomer(
    @PathVariable("uuid")
    UUID uuid
  ) {
    if (null == customers.remove(uuid)) {
      return ResponseEntity
        .notFound()
        .build();
    }
    return ResponseEntity
      .noContent()
      .build();
  }

}
