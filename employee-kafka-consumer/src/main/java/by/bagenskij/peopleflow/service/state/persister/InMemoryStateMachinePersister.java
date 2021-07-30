package by.bagenskij.peopleflow.service.state.persister;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.service.state.event.EmployeeEvent;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStateMachinePersister
    implements StateMachinePersist<EmployeeState, EmployeeEvent, Long> {

  private final Map<Long, StateMachineContext<EmployeeState, EmployeeEvent>> storage =
      new ConcurrentHashMap<>();

  @Override
  public void write(StateMachineContext<EmployeeState, EmployeeEvent> context, Long contextObj) {
    storage.put(contextObj, context);
  }

  @Override
  public StateMachineContext<EmployeeState, EmployeeEvent> read(Long contextObj) {
    return storage.get(contextObj);
  }
}
