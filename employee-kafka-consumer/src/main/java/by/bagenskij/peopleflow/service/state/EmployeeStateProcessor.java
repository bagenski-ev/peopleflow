package by.bagenskij.peopleflow.service.state;

public interface EmployeeStateProcessor {

  void process(Long id, EmployeeEvent event) throws Exception;
}
