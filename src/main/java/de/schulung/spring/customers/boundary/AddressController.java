package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/{uuid}/address")
@RequiredArgsConstructor
public class AddressController {

  private final CustomersService customersService;
  private final AddressDtoMapper mapper;

  @GetMapping
  AddressDto getAddress(
    @PathVariable("uuid")
    Customer customer
  ) {
    if (customer.getAddress() == null) {
      throw new NotFoundException();
    }
    return this.mapper.map(customer.getAddress());
  }

  @PutMapping
  ResponseEntity<Void> updateAddress(
    @PathVariable("uuid")
    Customer customer,
    @Valid
    @RequestBody
    AddressDto addressDto
  ) {
    var created = customer.getAddress() == null;
    var address = mapper.map(addressDto);
    customer.setAddress(address);
    customersService.update(customer);
    return ResponseEntity
      .status(created ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
      .build();
  }

}
