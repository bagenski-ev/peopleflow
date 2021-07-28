package by.bagenskij.peopleflow.service.state;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeStateMachineProcessor implements EmployeeStateProcessor {

  private final StateMachinePersister<EmployeeState, EmployeeEvent, Long> persister;

  private final StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

  public EmployeeStateMachineProcessor(
      StateMachinePersister<EmployeeState, EmployeeEvent, Long> persister,
      StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory) {
    this.persister = persister;
    this.stateMachineFactory = stateMachineFactory;
  }

  @Override
  public void process(Long id, EmployeeEvent event) throws Exception {
    StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineFactory.getStateMachine();
    stateMachine = persister.restore(stateMachine, id);

    stateMachine.getExtendedState().getVariables().put("employeeId", id);
    stateMachine.sendEvent(event);

    persister.persist(stateMachine, id);
  }
}
