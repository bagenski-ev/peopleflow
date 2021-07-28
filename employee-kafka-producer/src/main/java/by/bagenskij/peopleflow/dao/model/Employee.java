package by.bagenskij.peopleflow.dao.model;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
  @Column(name = "first_name")
  @NotBlank
  @Size(min = 3, max = 25, message = "First Name length must be between 3 and 25")
  private String firstName;

  @Column(name = "last_name")
  @NotBlank
  @Size(min = 3, max = 25, message = "Last Name length must be between 3 and 25")
  private String lastName;

  @Column(name = "date_of_birthday")
  @Past(message = "Date of Birthday must be in the past")
  private LocalDate dateOfBirthday;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  @NotNull
  private EmployeeState state;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contract_id", referencedColumnName = "id")
  private Contract contract;
}
