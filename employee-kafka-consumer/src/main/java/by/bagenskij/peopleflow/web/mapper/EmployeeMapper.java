package by.bagenskij.peopleflow.web.mapper;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ContractMapper.class})
public interface EmployeeMapper {
  Employee savingEmployeeDtoToEmployee(SavingEmployeeDto savingEmployeeDto);
}
