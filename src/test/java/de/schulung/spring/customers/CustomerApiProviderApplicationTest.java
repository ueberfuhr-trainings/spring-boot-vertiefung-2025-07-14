package de.schulung.spring.customers;

import de.schulung.spring.customers.shared.interceptors.AutoConfigureInterceptorTestComponents;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

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
// @Inherited
@Documented
// Spring
@SpringBootTest()
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
@RecordApplicationEvents
@AutoConfigureInterceptorTestComponents
public @interface CustomerApiProviderApplicationTest {

    /**
     * Whether the initialization of sample customer data should be
     * enabled or not.
     *
     * @return <tt>true</tt>, if the initialization of sample customer data should be
     * enabled, otherwise <tt>false</tt>
     */
    // This would allow a maximum of 2 contexts.
    @PropertyMapping("application.initialization.enabled")
    boolean initializationEnabled() default false;

}
