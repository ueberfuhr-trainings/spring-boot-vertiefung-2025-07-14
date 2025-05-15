package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.CustomersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;
import java.util.stream.Stream;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
class CustomersController {

  private final CustomersService customersService;
  private final CustomerDtoMapper mapper;

  @GetMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Stream<CustomerDto> getCustomers(
    @RequestParam(required = false)
    @Pattern(regexp = "active|locked|disabled")
    String state
  ) {
    return (
      null != state
        ? customersService.findAllByState(mapper.mapState(state))
        : customersService.findAll()
    )
      .map(mapper::map);
  }

  @PostMapping(
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<CustomerDto> createCustomer(
    @Valid
    @RequestBody
    CustomerDto customerDto
  ) {
    var customer = mapper.map(customerDto);
    customersService.create(customer);
    var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{uuid}")
      .buildAndExpand(customer.getUuid())
      .toUri();
    return ResponseEntity
      .created(location)
      .body(mapper.map(customer));
  }

  @GetMapping("/{uuid}")
  CustomerDto findCustomerById(
    @PathVariable("uuid")
    UUID uuid
  ) {
    return customersService
      .findById(uuid)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @DeleteMapping("/{uuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteCustomer(
    @PathVariable("uuid")
    UUID uuid
  ) {
    if (!customersService.delete(uuid)) {
      throw new NotFoundException();
    }
  }

}
