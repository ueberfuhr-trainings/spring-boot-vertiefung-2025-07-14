package de.schulung.spring.customers.domain.events;

import java.util.UUID;

public record CustomerDeletedEvent(
  UUID uuid
) {
}
