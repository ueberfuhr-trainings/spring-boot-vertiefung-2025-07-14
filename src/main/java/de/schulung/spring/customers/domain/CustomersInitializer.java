package de.schulung.spring.customers.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
  name = "application.initialization.enabled",
  havingValue = "true"
  // matchIfMissing = false
)
@Slf4j
public class CustomersInitializer {

  private final CustomersService customersService;

  // @Value("${application.initialization.enabled:false}")
  // boolean enabled;

  @EventListener(ApplicationReadyEvent.class)
  public void initialize() {
    log.info("Initializing customers...");
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
