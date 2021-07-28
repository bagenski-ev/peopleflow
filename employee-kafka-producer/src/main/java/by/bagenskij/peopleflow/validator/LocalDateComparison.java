package by.bagenskij.peopleflow.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LocalDateComparisonValidator.class)
@Documented
public @interface LocalDateComparison {

  String message() default "{Date is not earlier}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String earlierLocalDateFieldName();

  String comparedLocalDateFieldName();

  @Target({TYPE, ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    LocalDateComparison[] value();
  }
}
