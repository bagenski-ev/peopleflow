package by.bagenskij.peopleflow.service.impl;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.dao.repository.EmployeeRepository;
import by.bagenskij.peopleflow.service.EmployeeService;
import by.bagenskij.peopleflow.service.state.EmployeeEvent;
import by.bagenskij.peopleflow.service.state.EmployeeStateMachineProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee, Long, EmployeeRepository>
    implements EmployeeService {

  private final EmployeeStateMachineProcessor employeeStateMachineProcessor;

  protected EmployeeServiceImpl(
      EmployeeRepository repository, EmployeeStateMachineProcessor employeeStateMachineProcessor) {
    super(repository);
    this.employeeStateMachineProcessor = employeeStateMachineProcessor;
  }

  @Transactional
  @Override
  public Employee add(Employee entity) {
    entity.setState(EmployeeState.ADDED);
    return super.save(entity);
  }

  @Transactional
  @Override
  public void check(Long id) throws Exception {
    employeeStateMachineProcessor.process(id, EmployeeEvent.CHECK);
  }

  @Transactional
  @Override
  public void approve(Long id) throws Exception {
    employeeStateMachineProcessor.process(id, EmployeeEvent.APPROVE);
  }

  @Transactional
  @Override
  public void activate(Long id) throws Exception {
    employeeStateMachineProcessor.process(id, EmployeeEvent.ACTIVATE);
  }
}
