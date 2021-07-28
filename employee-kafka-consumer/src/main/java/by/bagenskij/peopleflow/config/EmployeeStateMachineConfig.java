package by.bagenskij.peopleflow.config;

import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.service.state.EmployeeEvent;
import by.bagenskij.peopleflow.service.state.InMemoryStateMachinePersister;
import by.bagenskij.peopleflow.service.state.UpdateEmployeeStateAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class EmployeeStateMachineConfig
    extends StateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

  private final UpdateEmployeeStateAction updateEmployeeStateAction;

  public EmployeeStateMachineConfig(UpdateEmployeeStateAction updateEmployeeStateAction) {
    this.updateEmployeeStateAction = updateEmployeeStateAction;
  }

  @Override
  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states)
      throws Exception {
    states
        .withStates()
        .initial(EmployeeState.ADDED)
        .states(EnumSet.allOf(EmployeeState.class))
        .end(EmployeeState.ACTIVE);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(EmployeeState.ADDED)
        .target(EmployeeState.IN_CHECK)
        .event(EmployeeEvent.CHECK)
        .action(updateEmployeeStateAction)
        .and()
        .withExternal()
        .source(EmployeeState.IN_CHECK)
        .target(EmployeeState.APPROVED)
        .event(EmployeeEvent.APPROVE)
        .action(updateEmployeeStateAction)
        .and()
        .withExternal()
        .source(EmployeeState.APPROVED)
        .target(EmployeeState.ACTIVE)
        .event(EmployeeEvent.ACTIVATE)
        .action(updateEmployeeStateAction);
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config)
      throws Exception {
    StateMachineListenerAdapter<EmployeeState, EmployeeEvent> adapter =
        new StateMachineListenerAdapter<>() {
          @Override
          public void stateChanged(
              State<EmployeeState, EmployeeEvent> from, State<EmployeeState, EmployeeEvent> to) {
            log.info("state changed from: {}, to: {}", from, to);
          }
        };
    config.withConfiguration().autoStartup(true).listener(adapter);
  }

  @Bean
  public StateMachinePersister persister() {
    return new DefaultStateMachinePersister<>(new InMemoryStateMachinePersister());
  }
}
