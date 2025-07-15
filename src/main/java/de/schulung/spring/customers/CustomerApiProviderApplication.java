package de.schulung.spring.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CustomerApiProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerApiProviderApplication.class, args);
  }

}
