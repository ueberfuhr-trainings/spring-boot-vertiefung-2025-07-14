package de.schulung.spring.customers.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
@RequiredArgsConstructor
public class CustomersInitializer {

  private final CustomersService customersService;

  @EventListener(ApplicationReadyEvent.class)
  public void initialize() {
    if (customersService.count() == 0) {
      customersService.create(
        Customer
          .builder()
          .name("Tom Mayer")
          .birthdate(LocalDate.of(1995, Month.OCTOBER, 20))
          .build()
      );
    }
  }

}
