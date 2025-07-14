package de.schulung.spring.customers.boundary;

import de.schulung.spring.customers.domain.CustomersService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
  ElementType.TYPE,
  ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// Spring
@WebMvcTest
@ComponentScan(
  basePackageClasses = BoundaryTest.class
)
@MockitoBean(types = CustomersService.class)
@AutoConfigureCors(allowedOrigins = "*.swagger.io")
@ActiveProfiles("test")
public @interface BoundaryTest {
}
