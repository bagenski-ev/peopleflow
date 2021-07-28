package by.bagenskij.peopleflow.it.service.state;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.state.EmployeeEvent;
import by.bagenskij.peopleflow.service.state.UpdateEmployeeStateAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UpdateEmployeeStateActionTest {

  @MockBean private EmployeeRepository employeeRepository;

  @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
  private StateContext<EmployeeState, EmployeeEvent> stateContext;

  private UpdateEmployeeStateAction action;

  @BeforeEach
  public void beforeEach() {
    action = new UpdateEmployeeStateAction(employeeRepository);
  }

  @Test
  public void update_employee_state_action_test_success() {
    Long employeeId = 1L;
    Employee employee = new Employee();
    employee.setId(employeeId);
    employee.setState(EmployeeState.ADDED);

    when(stateContext.getStateMachine().getExtendedState().getVariables().get("employeeId"))
        .thenReturn(employeeId);
    when(stateContext.getTarget().getId()).thenReturn(EmployeeState.IN_CHECK);
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

    action.execute(stateContext);

    verify(employeeRepository, times(1)).findById(employeeId);
    verify(employeeRepository, times(1)).save(eq(employee));
  }

  @Test
  public void update_unknown_employee_state_action_test_success() {
    Long unknownEmployeeId = 1L;

    when(stateContext.getStateMachine().getExtendedState().getVariables().get("employeeId"))
        .thenReturn(unknownEmployeeId);
    when(employeeRepository.findById(unknownEmployeeId)).thenReturn(Optional.empty());

    action.execute(stateContext);

    verify(employeeRepository, times(1)).findById(unknownEmployeeId);
    verify(employeeRepository, never()).save(any(Employee.class));
  }
}
