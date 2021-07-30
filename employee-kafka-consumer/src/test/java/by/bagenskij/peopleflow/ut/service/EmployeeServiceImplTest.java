package by.bagenskij.peopleflow.ut.service;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.impl.EmployeeServiceImpl;
import by.bagenskij.peopleflow.service.state.event.EmployeeEvent;
import by.bagenskij.peopleflow.service.state.processor.EmployeeStateProcessorImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.bagenskij.peopleflow.dao.model.enums.EmployeeState.*;
import static by.bagenskij.peopleflow.service.state.event.EmployeeEvent.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

  @Mock private EmployeeStateProcessorImpl mockEmployeeStateProcessorImpl;
  @Mock private EmployeeRepository mockEmployeeRepository;
  @InjectMocks private EmployeeServiceImpl employeeServiceIml;

  @Test
  public void test_add_employee_success() {
    Employee mockEmployee = mock(Employee.class);

    employeeServiceIml.add(mockEmployee);

    verify(mockEmployee, times(1)).setState(ADDED);
    verify(mockEmployeeRepository, times(1)).save(mockEmployee);
  }

  @SneakyThrows
  @Test
  public void test_check_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.check(employeeId);

    verify(mockEmployeeStateProcessorImpl, times(1)).process(employeeId, CHECK);
  }

  @SneakyThrows
  @Test
  public void test_approve_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.approve(employeeId);

    verify(mockEmployeeStateProcessorImpl, times(1)).process(employeeId, APPROVE);
  }

  @SneakyThrows
  @Test
  public void test_activate_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.activate(employeeId);

    verify(mockEmployeeStateProcessorImpl, times(1)).process(employeeId, ACTIVATE);
  }
}
