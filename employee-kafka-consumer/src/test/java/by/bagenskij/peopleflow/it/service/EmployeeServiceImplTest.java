package by.bagenskij.peopleflow.it.service;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.state.EmployeeEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import java.util.Optional;

import static by.bagenskij.peopleflow.dao.model.enums.EmployeeState.*;
import static by.bagenskij.peopleflow.service.state.EmployeeEvent.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceImplTest {

  @Autowired private StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;
  @MockBean private EmployeeRepository employeeRepository;

  private static int defaultAwaitTime = 5;

  @Test
  public void test_employee_state_change_from_initial_to_end_success() throws Exception {
    StateMachine<EmployeeState, EmployeeEvent> machine = stateMachineFactory.getStateMachine();
    Long employeeId = 1L;
    machine.getExtendedState().getVariables().put("employeeId", employeeId);

    when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));

    StateMachineTestPlan<EmployeeState, EmployeeEvent> plan =
        StateMachineTestPlanBuilder.<EmployeeState, EmployeeEvent>builder()
            .defaultAwaitTime(defaultAwaitTime)
            .stateMachine(machine)
            .step()
            .expectStates(ADDED)
            .expectStateChanged(0)
            .and()
            .step()
            .sendEvent(CHECK)
            .expectState(IN_CHECK)
            .expectStateChanged(1)
            .and()
            .step()
            .sendEvent(APPROVE)
            .expectState(APPROVED)
            .expectStateChanged(1)
            .and()
            .step()
            .sendEvent(ACTIVATE)
            .expectState(ACTIVE)
            .expectStateChanged(1)
            .and()
            .build();
    plan.test();
  }

}
