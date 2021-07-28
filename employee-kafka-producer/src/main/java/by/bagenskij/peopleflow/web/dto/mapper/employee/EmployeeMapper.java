package by.bagenskij.peopleflow.web.dto.mapper.employee;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.web.dto.mapper.BaseEntityMapperConfig;
import by.bagenskij.peopleflow.web.dto.mapper.contract.ContractMapper;
import by.bagenskij.peopleflow.web.dto.model.employee.EmployeeDto;
import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
    config = BaseEntityMapperConfig.class,
    uses = {ContractMapper.class})
public interface EmployeeMapper {
  Employee map(EmployeeDto employeeDto);

  EmployeeDto map(Employee employee);

  Employee map(SavingEmployeeDto savingEmployeeDto);

  List<EmployeeDto> map(List<Employee> employees);
}
