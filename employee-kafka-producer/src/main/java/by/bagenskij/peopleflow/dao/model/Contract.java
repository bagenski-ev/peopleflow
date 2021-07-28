package by.bagenskij.peopleflow.dao.model;

import by.bagenskij.peopleflow.validator.LocalDateComparison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@LocalDateComparison(
    earlierLocalDateFieldName = "startDate",
    comparedLocalDateFieldName = "endDate")
@Entity
@Table(name = "contract")
public class Contract extends BaseEntity {

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "job_title")
  @NotBlank
  @Size(min = 3, max = 35, message = "Job Title length must be between 3 and 35")
  private String jobTitle;

  @Column(name = "hours_of_work")
  @Min(value = 1, message = "Hours of Work value must be between 1 and 8")
  @Max(value = 8, message = "Hours of Work value must be between 1 and 8")
  private int hoursOfWork;

  @Column(name = "salary")
  @DecimalMin(value = "100", message = "Salary must be greater than 100")
  private BigDecimal salary;

  @OneToOne(mappedBy = "contract")
  private Employee employee;
}
