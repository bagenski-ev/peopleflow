package by.bagenskij.peopleflow.web.dto.model.contract;

import by.bagenskij.peopleflow.validator.LocalDateComparison;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(value = "The Contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@LocalDateComparison(
    earlierLocalDateFieldName = "startDate",
    comparedLocalDateFieldName = "endDate")
public class ContractDto {
  @ApiModelProperty(
      name = "Start Date",
      value = "The Employee Start Date of the Contract",
      example = "2008-12-03")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull
  private LocalDate startDate;

  @ApiModelProperty(
      name = "End Date",
      value = "The Employee End Date of the Contract",
      example = "2013-12-03")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull
  private LocalDate endDate;

  @ApiModelProperty(
      name = "Job Title",
      value = "The Employee Job Title",
      example = "Software Engineer")
  @NotBlank
  @Size(min = 3, max = 35, message = "Job Title length must be between 3 and 35")
  private String jobTitle;

  @ApiModelProperty(
      name = "Hours of Work",
      value = "The Employee number of working hours per day",
      example = "8")
  @Min(value = 1, message = "Hours of Work value must be between 1 and 8")
  @Max(value = 8, message = "Hours of Work value must be between 1 and 8")
  private int hoursOfWork;

  @ApiModelProperty(name = "Salary", value = "The Employee Salary for month in $", example = "3600")
  @DecimalMin(value = "100", message = "Salary must be greater than 100")
  private BigDecimal salary;
}
