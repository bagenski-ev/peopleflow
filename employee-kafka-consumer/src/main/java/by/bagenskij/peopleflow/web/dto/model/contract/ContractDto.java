package by.bagenskij.peopleflow.web.dto.model.contract;

import by.bagenskij.peopleflow.validator.LocalDateComparison;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@LocalDateComparison(
    earlierLocalDateFieldName = "startDate",
    comparedLocalDateFieldName = "endDate")
public class ContractDto {
  @NotNull private LocalDate startDate;
  @NotNull private LocalDate endDate;

  @NotBlank
  @Size(min = 3, max = 35, message = "Job Title length must be between 3 and 35")
  private String jobTitle;

  @Min(value = 1, message = "Hours of Work value must be between 1 and 8")
  @Max(value = 8, message = "Hours of Work value must be between 1 and 8")
  private int hoursOfWork;

  @DecimalMin(value = "100", message = "Salary must be greater than 100")
  private BigDecimal salary;
}
