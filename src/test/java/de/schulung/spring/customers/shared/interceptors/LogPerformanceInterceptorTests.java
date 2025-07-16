package de.schulung.spring.customers.shared.interceptors;

import de.schulung.spring.customers.CustomerApiProviderApplicationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * Test-Konzept:
 *  - Spring Boot Test, weil Interceptors nur mit Spring und Dependency Injection
 *    funktionieren
 *    => Context-Auswahl: komplette Plattform (alle Packages)
 *  - KEIN Testen des CustomersService, weil keine fachliche Anforderung
 *    => Testen des Interceptors unabhängig von dessen Verwendung im Anwendungscode
 *    => eigene Bean für den Test:
 *       - LogPerformanceTestService
 *       - LogPerformanceTestAtClassLevelService
 */
@CustomerApiProviderApplicationTest
@ExtendWith(OutputCaptureExtension.class)
class LogPerformanceInterceptorTests {

  @Autowired
  LogPerformanceTestService logPerformanceTestService;
  @Autowired
  LogPerformanceTestAtClassLevelService logPerformanceTestAtClassLevelService;

  @Test
  void shouldInvokeMethodPerformanceLogging(CapturedOutput output) {
    logPerformanceTestService.doSth();
    assertThat(output)
      .containsPattern(String.format("(?i).*%s cost \\d+ ms.*", "doSth"));
  }

  @Test
  void shouldInvokeMethodPerformanceLoggingWithClassLevelAnnotation(CapturedOutput output) {
    logPerformanceTestAtClassLevelService.doSth();
    assertThat(output)
      .containsPattern(String.format("(?i).*%s cost \\d+ ms.*", "doSth"));
  }

}
