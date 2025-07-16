package de.schulung.spring.customers.shared.interceptors;

import de.schulung.spring.customers.CustomerApiProviderApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@CustomerApiProviderApplicationTest
class PublishEventInterceptorTests {

  @Autowired
  PublishEventTestService service;
  @Autowired
  ApplicationEvents applicationEvents;

  @Test
  void shouldPublishEvent() {
    var payload = new Object();
    service.doAndPublishEvent(payload);
    assertThat(applicationEvents.stream(PublishEventTestEvent.class))
      .hasSize(1)
      .first()
      .extracting(PublishEventTestEvent::payload)
      .isSameAs(payload);
  }

  @Test
  void shouldNotPublishEventOnException() {
    var payload = new Object();
    assertThatThrownBy(() -> service.doThrowException(payload))
      .isNotNull();
    assertThat(applicationEvents.stream(PublishEventTestEvent.class))
      .isEmpty();
  }

  @Test
  void shouldNotPublishEventOnNonMatchingConstructorArgument() {
    assertThatThrownBy(service::doNonMatchingConstructorArgument)
      .isNotNull();
    assertThat(applicationEvents.stream(PublishEventTestEvent.class))
      .isEmpty();
  }

}
