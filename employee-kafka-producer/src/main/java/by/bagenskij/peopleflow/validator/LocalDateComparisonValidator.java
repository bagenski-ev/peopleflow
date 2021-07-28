package by.bagenskij.peopleflow.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

@Slf4j
public class LocalDateComparisonValidator
    implements ConstraintValidator<LocalDateComparison, Object> {

  private String earlierLocalDateFieldName;
  private String comparedLocalDateFieldName;

  @Override
  public void initialize(LocalDateComparison constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    this.earlierLocalDateFieldName = constraintAnnotation.earlierLocalDateFieldName();
    this.comparedLocalDateFieldName = constraintAnnotation.comparedLocalDateFieldName();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      final LocalDate earlierLocalDate =
          LocalDate.parse(BeanUtils.getProperty(value, earlierLocalDateFieldName));
      final LocalDate comparedLocalDate =
          LocalDate.parse(BeanUtils.getProperty(value, comparedLocalDateFieldName));

      return earlierLocalDate.isBefore(comparedLocalDate);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      log.error("Error during the Date Comparison:", e);
      return false;
    }
  }
}
