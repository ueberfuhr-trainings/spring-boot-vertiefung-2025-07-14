package de.schulung.spring.customers.shared.interceptors;

import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class PublishEventTestService {

  @PublishEvent(PublishEventTestEvent.class)
  public void doAndPublishEvent(Object payload) {
    // nothing to do
  }

  @PublishEvent(PublishEventTestEvent.class)
  public void doThrowException(Object payload) {
    throw new RuntimeException();
  }

  @PublishEvent(PublishEventTestEvent.class)
  public void doNonMatchingConstructorArgument() {
    // nothing to do
  }

}
