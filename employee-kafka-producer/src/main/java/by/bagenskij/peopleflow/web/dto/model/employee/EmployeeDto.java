package by.bagenskij.peopleflow.web.dto.model.employee;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.web.dto.model.BaseEntityDto;
import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel(
    value = "The Employee",
    subTypes = {ContractDto.class})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto extends BaseEntityDto {

  @ApiModelProperty(name = "First Name", example = "Bob", value = "The First Name of the Employee")
  @NotBlank
  @Size(min = 3, max = 25, message = "First Name length must be between 3 and 25")
  private String firstName;

  @ApiModelProperty(name = "Last Name", example = "Dylan", value = "The Last Name of the Employee")
  @NotBlank
  @Size(min = 3, max = 25, message = "Last Name length must be between 3 and 25")
  private String lastName;

  @ApiModelProperty(
      name = "Date of Birthday",
      example = "2007-12-03",
      value = "The Birthday of the Employee")
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Past(message = "Date of Birthday must be in the past")
  private LocalDate dateOfBirthday;

  @ApiModelProperty(name = "State", example = "ACTIVE", value = "The Employee State")
  @NotNull
  private EmployeeState state;

  @ApiModelProperty(name = "Contract", value = "The Contract of the Employee")
  @Valid
  @NotNull
  private ContractDto contract;
}
