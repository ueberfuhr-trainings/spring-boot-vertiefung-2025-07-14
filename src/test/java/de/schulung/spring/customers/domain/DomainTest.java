package de.schulung.spring.customers.domain;

import de.schulung.spring.customers.CustomerApiProviderApplicationTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation providing a Spring context that
 * <ul>
 *   <li>initializes the domain</li>
 *   <li>uses an in-memory implementation of {@link CustomersSink}</li>
 * </ul>
 */
@Target({
  ElementType.ANNOTATION_TYPE,
  ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// Spring
// for domain tests, simply re-use the whole platform
@CustomerApiProviderApplicationTest
public @interface DomainTest {

  /**
   * Whether the initialization of sample customer data should be
   * enabled or not.
   *
   * @return <tt>true</tt>, if the initialization of sample customer data should be
   * enabled, otherwise <tt>false</tt>
   */
  // This would allow a maximum of 2 contexts.
  @AliasFor(
    annotation = CustomerApiProviderApplicationTest.class,
    attribute = "initializationEnabled"
  )
  boolean initializationEnabled() default false;

}
