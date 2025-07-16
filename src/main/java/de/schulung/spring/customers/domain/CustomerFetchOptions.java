package de.schulung.spring.customers.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerFetchOptions {

  public static CustomerFetchOptions DEFAULT = CustomerFetchOptions.builder().build();

  @Builder.Default
  private boolean enableAddress = false;

}
