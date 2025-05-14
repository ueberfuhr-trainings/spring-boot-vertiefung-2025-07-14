package de.schulung.spring.customers.boundary;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  WebMvcConfigurer registerStaticResourceTypes() {
    return new WebMvcConfigurer() {
      @Override
      public void configureContentNegotiation(
        @Nonnull
        ContentNegotiationConfigurer configurer
      ) {
        configurer
          .mediaType("yml", MediaType.APPLICATION_YAML)
          .mediaType("yaml", MediaType.APPLICATION_YAML);
      }
    };
  }

}
