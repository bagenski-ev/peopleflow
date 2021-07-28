package by.bagenskij.peopleflow.web.dto.model.employee;

import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel(
    value = "The Saving Employee",
    subTypes = {ContractDto.class})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingEmployeeDto {

  @ApiModelProperty(name = "First Name", example = "Bob", value = "The First Name of the Employee")
  @Size(min = 3, max = 25, message = "First Name length must be between 3 and 25")
  @NotBlank
  private String firstName;

  @ApiModelProperty(name = "Last Name", example = "Dylan", value = "The Last Name of the Employee")
  @Size(min = 3, max = 25, message = "Last Name length must be between 3 and 25")
  @NotBlank
  private String lastName;

  @ApiModelProperty(
      name = "Date of Birthday",
      example = "2007-12-03",
      value = "The Birthday of the Employee")
  @Past(message = "Date of Birthday must be in the past")
  @NotNull
  private LocalDate dateOfBirthday;

  @ApiModelProperty(name = "Contract", value = "The Contract of the Employee")
  @Valid
  @NotNull
  private ContractDto contract;
}
