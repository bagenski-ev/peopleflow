package by.bagenskij.peopleflow.service.state.processor;

import by.bagenskij.peopleflow.service.state.event.EmployeeEvent;

public interface EmployeeStateProcessor {
  void process(Long id, EmployeeEvent event) throws Exception;
}
