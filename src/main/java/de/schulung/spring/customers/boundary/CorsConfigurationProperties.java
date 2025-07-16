package de.schulung.spring.customers.boundary;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("application.cors")
public class CorsConfigurationProperties {

  private boolean enabled = false;
  private String[] allowedOrigins = {"*"};
  private boolean allowCredentials;

}
