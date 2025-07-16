package de.schulung.spring.customers.shared.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AnnotationUtility {

  public static <T extends Annotation> Optional<T> findAnnotation(Method method, Class<T> annotationType) {
    var methodAnnotation = AnnotationUtils.findAnnotation(method, annotationType);
    return
      null != methodAnnotation
        ? Optional.of(methodAnnotation)
        : Optional.ofNullable(AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType));
  }

}
