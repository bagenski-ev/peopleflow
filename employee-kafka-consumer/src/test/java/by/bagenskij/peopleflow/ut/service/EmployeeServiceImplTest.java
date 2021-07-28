package by.bagenskij.peopleflow.ut.service;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.impl.EmployeeServiceImpl;
import by.bagenskij.peopleflow.service.state.EmployeeEvent;
import by.bagenskij.peopleflow.service.state.EmployeeStateMachineProcessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

  @Mock private EmployeeStateMachineProcessor mockEmployeeStateMachineProcessor;
  @Mock private EmployeeRepository mockEmployeeRepository;
  @InjectMocks private EmployeeServiceImpl employeeServiceIml;

  @Test
  public void test_add_employee_success() {
    Employee mockEmployee = mock(Employee.class);

    employeeServiceIml.add(mockEmployee);

    verify(mockEmployee, times(1)).setState(EmployeeState.ADDED);
    verify(mockEmployeeRepository, times(1)).save(mockEmployee);
  }

  @SneakyThrows
  @Test
  public void test_check_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.check(employeeId);

    verify(mockEmployeeStateMachineProcessor, times(1)).process(employeeId, EmployeeEvent.CHECK);
  }

  @SneakyThrows
  @Test
  public void test_approve_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.approve(employeeId);

    verify(mockEmployeeStateMachineProcessor, times(1)).process(employeeId, EmployeeEvent.APPROVE);
  }

  @SneakyThrows
  @Test
  public void test_activate_employee_success() {
    Long employeeId = 1L;

    employeeServiceIml.activate(employeeId);

    verify(mockEmployeeStateMachineProcessor, times(1)).process(employeeId, EmployeeEvent.ACTIVATE);
  }
}
