package by.bagenskij.peopleflow.service.state.processor;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.service.state.event.EmployeeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import static by.bagenskij.peopleflow.Constants.EMPLOYEE_ID_HEADER;

@Slf4j
@Service
public class EmployeeStateProcessorImpl implements EmployeeStateProcessor {

  private final StateMachinePersister<EmployeeState, EmployeeEvent, Long> persister;

  private final StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

  public EmployeeStateProcessorImpl(
      StateMachinePersister<EmployeeState, EmployeeEvent, Long> persister,
      StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory) {
    this.persister = persister;
    this.stateMachineFactory = stateMachineFactory;
  }

  @Override
  public void process(Long id, EmployeeEvent event) throws Exception {
    StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineFactory.getStateMachine();
    stateMachine = persister.restore(stateMachine, id);

    stateMachine.getExtendedState().getVariables().put(EMPLOYEE_ID_HEADER, id);
    stateMachine.sendEvent(event);

    persister.persist(stateMachine, id);
  }
}
