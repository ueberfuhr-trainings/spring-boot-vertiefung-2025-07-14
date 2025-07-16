package de.schulung.spring.customers.shared.interceptors;

import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class LogPerformanceTestService {

  @LogPerformance
  void doSth() {
  }

}
