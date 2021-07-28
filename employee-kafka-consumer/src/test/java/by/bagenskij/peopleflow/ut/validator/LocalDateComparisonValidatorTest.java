package by.bagenskij.peopleflow.ut.validator;

import by.bagenskij.peopleflow.validator.LocalDateComparison;
import by.bagenskij.peopleflow.validator.LocalDateComparisonValidator;
import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocalDateComparisonValidatorTest {

  @Mock private LocalDateComparison mockLocalDateComparisonAnnotation;

  @Mock private ConstraintValidatorContext mockConstraintValidatorContext;

  @Test
  public void testIsValidSuccessWithCorrectDateValues() {
    when(mockLocalDateComparisonAnnotation.earlierLocalDateFieldName()).thenReturn("startDate");
    when(mockLocalDateComparisonAnnotation.comparedLocalDateFieldName()).thenReturn("endDate");

    LocalDateComparisonValidator localDateComparisonValidator = new LocalDateComparisonValidator();
    localDateComparisonValidator.initialize(mockLocalDateComparisonAnnotation);

    ContractDto contractDto = new ContractDto();
    contractDto.setStartDate(LocalDate.MIN);
    contractDto.setEndDate(LocalDate.MAX);

    assertThat(
        localDateComparisonValidator.isValid(contractDto, mockConstraintValidatorContext),
        is(true));
  }

  @Test
  public void testIsValidNotSuccessWithIncorrectDateValues() {
    when(mockLocalDateComparisonAnnotation.earlierLocalDateFieldName()).thenReturn("startDate");
    when(mockLocalDateComparisonAnnotation.comparedLocalDateFieldName()).thenReturn("endDate");

    LocalDateComparisonValidator localDateComparisonValidator = new LocalDateComparisonValidator();
    localDateComparisonValidator.initialize(mockLocalDateComparisonAnnotation);

    ContractDto contractDto = new ContractDto();
    contractDto.setStartDate(LocalDate.MAX);
    contractDto.setEndDate(LocalDate.MIN);

    assertThat(
        localDateComparisonValidator.isValid(contractDto, mockConstraintValidatorContext),
        is(false));
  }

  @Test
  public void testIsValidNotSuccessWithEqualsDateValues() {
    when(mockLocalDateComparisonAnnotation.earlierLocalDateFieldName()).thenReturn("startDate");
    when(mockLocalDateComparisonAnnotation.comparedLocalDateFieldName()).thenReturn("endDate");

    LocalDateComparisonValidator localDateComparisonValidator = new LocalDateComparisonValidator();
    localDateComparisonValidator.initialize(mockLocalDateComparisonAnnotation);

    ContractDto contractDto = new ContractDto();
    contractDto.setStartDate(LocalDate.MAX);
    contractDto.setEndDate(LocalDate.MAX);

    assertThat(
        localDateComparisonValidator.isValid(contractDto, mockConstraintValidatorContext),
        is(false));
  }
}
