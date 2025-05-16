package de.schulung.spring.customers.boundary;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpHeaders.*;

@Configuration
@ConditionalOnProperty(
  name = "application.cors.enabled",
  havingValue = "true"
)
public class CorsConfiguration {

  @Bean
  WebMvcConfigurer corsConfigurer(
    @Value("${application.cors.allowed-origins}") final String allowedOrigins
  ) {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@Nonnull CorsRegistry registry) {
        registry
          .addMapping("/**")
          .exposedHeaders(LOCATION, LINK)
          .allowedHeaders(ORIGIN, CONTENT_TYPE, ACCEPT, ACCEPT_LANGUAGE, IF_MATCH, IF_NONE_MATCH, AUTHORIZATION)
          .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
          .allowedOriginPatterns(allowedOrigins.split(","))
          .allowCredentials(false);
      }
    };
  }

}
