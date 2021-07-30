package by.bagenskij.peopleflow.web.controller;

import by.bagenskij.peopleflow.kafka.producer.KafkaProducer;
import by.bagenskij.peopleflow.service.EmployeeService;
import by.bagenskij.peopleflow.web.dto.mapper.employee.EmployeeMapper;
import by.bagenskij.peopleflow.web.dto.model.employee.EmployeeDto;
import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/employees")
@Api(tags = "Employees")
public class EmployeeController {

  private final KafkaProducer kafkaProducer;
  private final EmployeeMapper employeeMapper;
  private final EmployeeService employeeService;

  @Value("${spring.kafka.topic.employees-for-saving}")
  private String employeeForSavingTopic;

  @Value("${spring.kafka.topic.employees-for-check}")
  private String employeeCheckedTopic;

  @Value("${spring.kafka.topic.employees-for-approval}")
  private String employeesForApprovalTopic;

  @Value("${spring.kafka.topic.employees-for-activation}")
  private String employeesForActivationTopic;

  public EmployeeController(
      KafkaProducer kafkaProducer, EmployeeMapper employeeMapper, EmployeeService employeeService) {
    this.kafkaProducer = kafkaProducer;
    this.employeeMapper = employeeMapper;
    this.employeeService = employeeService;
  }

  @PostMapping
  @ApiOperation(value = "Saves a new Employee", notes = "Saves a new Employee")
  @ApiResponse(
      code = 202,
      message = "The SavingEmployeeDto was successfully send to the specified kafka topic")
  public ResponseEntity<Void> saveEmployee(
      @Valid @RequestBody @ApiParam(value = "The Saving Employee")
          SavingEmployeeDto savingEmployeeDto) {
    kafkaProducer.send(employeeForSavingTopic, savingEmployeeDto);
    return ResponseEntity.status(ACCEPTED).build();
  }

  @PutMapping("/{id}/check")
  @ApiOperation(value = "Check an Employee by id", notes = "Check an Employee by id")
  @ApiResponse(
      code = 202,
      message = "The task to check the Employee was successfully send to the specified kafka topic")
  public ResponseEntity<Void> checkEmployee(
      @PathVariable("id") @ApiParam(value = "The Employee ID", example = "1") Long id) {
    kafkaProducer.send(employeeCheckedTopic, id);
    return ResponseEntity.status(ACCEPTED).build();
  }

  @PutMapping("/{id}/approve")
  @ApiOperation(value = "Approve an Employee by id", notes = "Approve an Employee by id")
  @ApiResponse(
      code = 202,
      message =
          "The task to approve the Employee was successfully send to the specified kafka topic")
  public ResponseEntity<Void> approveEmployee(
      @PathVariable("id") @ApiParam(value = "The Employee ID", example = "1") Long id) {
    kafkaProducer.send(employeesForApprovalTopic, id);
    return ResponseEntity.status(ACCEPTED).build();
  }

  @PutMapping("/{id}/activate")
  @ApiOperation(value = "Activate an Employee by id", notes = "Activate an Employee by id")
  @ApiResponse(
      code = 202,
      message =
          "The task to activate the Employee was successfully send to the specified kafka topic")
  public ResponseEntity<Void> activateEmployee(
      @PathVariable("id") @ApiParam(value = "The Employee ID", example = "1") Long id) {
    kafkaProducer.send(employeesForActivationTopic, id);
    return ResponseEntity.status(ACCEPTED).build();
  }

  @GetMapping
  @ApiOperation(value = "Get All Employees", notes = "Get all Employees")
  @ApiResponse(code = 200, message = "All employees were received successfully")
  public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
    return ResponseEntity.status(OK).body(employeeMapper.map(employeeService.findAll()));
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get an Employee by id", notes = "Get an Employee by id")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "An Employee was received successfully"),
        @ApiResponse(code = 400, message = "And Employee with passed id was not found")
      })
  public ResponseEntity<EmployeeDto> getEmployee(
      @PathVariable("id") @ApiParam(value = "The Employee ID", example = "1") Long id) {
    try {
      return ResponseEntity.status(OK).body(employeeMapper.map(employeeService.findById(id)));
    } catch (NoSuchElementException e) {
      log.error(String.format("No employee with id=%d", id), e);
      return ResponseEntity.status(NOT_FOUND).build();
    }
  }
}
