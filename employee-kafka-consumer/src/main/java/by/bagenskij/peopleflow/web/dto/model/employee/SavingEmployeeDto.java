package by.bagenskij.peopleflow.web.dto.model.employee;

import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingEmployeeDto {

  @NotBlank
  @Size(min = 3, max = 25, message = "First Name length must be between 3 and 25")
  private String firstName;

  @NotBlank
  @Size(min = 3, max = 25, message = "Last Name length must be between 3 and 25")
  private String lastName;

  @NotNull
  @Past(message = "Date of Birthday must be in the past")
  private LocalDate dateOfBirthday;

  @Valid @NotNull private ContractDto contract;
}
