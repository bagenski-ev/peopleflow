package by.bagenskij.peopleflow.service.state;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Component
public class UpdateEmployeeStateAction implements Action<EmployeeState, EmployeeEvent> {

  private final EmployeeRepository employeeRepository;

  public UpdateEmployeeStateAction(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  @Transactional
  public void execute(StateContext<EmployeeState, EmployeeEvent> stateContext) {
    Long employeeId =
        (Long) stateContext.getStateMachine().getExtendedState().getVariables().get("employeeId");
    try {
      Employee employee = employeeRepository.findById(employeeId).orElseThrow();
      EmployeeState state = stateContext.getTarget().getId();
      employee.setState(state);
      employeeRepository.save(employee);
    } catch (NoSuchElementException e) {
      log.error(String.format("No employee with id=%d", employeeId), e);
    }
  }
}
