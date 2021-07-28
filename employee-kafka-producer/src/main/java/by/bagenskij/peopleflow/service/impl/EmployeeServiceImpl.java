package by.bagenskij.peopleflow.service.impl;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee, Long, EmployeeRepository>
    implements EmployeeService {

  protected EmployeeServiceImpl(EmployeeRepository repository) {
    super(repository);
  }
}
