package by.bagenskij.peopleflow.service;

import by.bagenskij.peopleflow.dao.model.Employee;

public interface EmployeeService extends IService<Employee, Long> {

  Employee add(Employee entity);

  void check(Long id) throws Exception;

  void approve(Long id) throws Exception;

  void activate(Long id) throws Exception;
}
