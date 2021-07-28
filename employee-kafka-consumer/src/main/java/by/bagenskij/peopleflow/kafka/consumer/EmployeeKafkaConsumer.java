package by.bagenskij.peopleflow.kafka.consumer;

import by.bagenskij.peopleflow.service.EmployeeService;
import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import by.bagenskij.peopleflow.web.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Slf4j
public class EmployeeKafkaConsumer {

  private final EmployeeService employeeService;
  private final EmployeeMapper employeeMapper;

  public EmployeeKafkaConsumer(EmployeeService employeeService, EmployeeMapper employeeMapper) {
    this.employeeService = employeeService;
    this.employeeMapper = employeeMapper;
  }

  @KafkaListener(
      topics = "${spring.kafka.topic.employees-for-saving}",
      containerFactory = "savingEmployeeKafkaListenerContainerFactory",
      groupId = "${spring.kafka.group}")
  public void addEmployee(SavingEmployeeDto savingEmployeeDto) {
    employeeService.add(employeeMapper.savingEmployeeDtoToEmployee(savingEmployeeDto));
  }

  @KafkaListener(
      topics = "${spring.kafka.topic.employees-for-check}",
      containerFactory = "longKafkaListenerContainerFactory",
      groupId = "${spring.kafka.group}")
  public void checkEmployee(Long employeeId) {
    try {
      employeeService.check(employeeId);
    } catch (NoSuchElementException e) {
      log.error(String.format("No employee with id=%d", employeeId), e);
    } catch (Exception e) {
      log.error("Error during the employee check", e);
    }
  }

  @KafkaListener(
      topics = "${spring.kafka.topic.employees-for-approval}",
      containerFactory = "longKafkaListenerContainerFactory",
      groupId = "${spring.kafka.group}")
  public void approve(Long employeeId) {
    try {
      employeeService.approve(employeeId);
    } catch (NoSuchElementException e) {
      log.error(String.format("No employee with id=%d", employeeId), e);
    } catch (Exception e) {
      log.error("Error during the employee approval", e);
    }
  }

  @KafkaListener(
      topics = "${spring.kafka.topic.employees-for-activation}",
      containerFactory = "longKafkaListenerContainerFactory",
      groupId = "${spring.kafka.group}")
  public void activate(Long employeeId) {
    try {
      employeeService.activate(employeeId);
    } catch (NoSuchElementException e) {
      log.error(String.format("No employee with id=%d", employeeId), e);
    } catch (Exception e) {
      log.error("Error during the employee activation", e);
    }
  }
}
