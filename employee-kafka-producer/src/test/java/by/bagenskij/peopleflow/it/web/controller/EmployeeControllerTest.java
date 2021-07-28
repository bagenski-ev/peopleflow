package by.bagenskij.peopleflow.it.web.controller;

import by.bagenskij.peopleflow.dao.model.Employee;
import by.bagenskij.peopleflow.dao.model.enums.EmployeeState;
import by.bagenskij.peopleflow.kafka.producer.KafkaProducer;
import by.bagenskij.peopleflow.service.EmployeeService;
import by.bagenskij.peopleflow.web.dto.mapper.employee.EmployeeMapper;
import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import by.bagenskij.peopleflow.web.dto.model.employee.EmployeeDto;
import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private EmployeeService employeeService;
  @MockBean private KafkaProducer kafkaProducer;
  @MockBean private EmployeeMapper employeeMapper;

  @Value("${spring.kafka.topic.employees-for-saving}")
  private String employeeForSavingTopic;

  @Value("${spring.kafka.topic.employees-for-check}")
  private String employeeForCheckTopic;

  @Value("${spring.kafka.topic.employees-for-approval}")
  private String employeesForApprovalTopic;

  @Value("${spring.kafka.topic.employees-for-activation}")
  private String employeesForActivationTopic;

  @Test
  public void test_get_all_employees_return_json_array_success() throws Exception {
    Employee employee = new Employee("Bob", "Dylan", LocalDate.now(), EmployeeState.ADDED, null);
    EmployeeDto employeeDto =
        new EmployeeDto("Bob", "Dylan", LocalDate.now(), EmployeeState.ADDED, null);

    List<Employee> employees = List.of(employee);
    when(employeeService.findAll()).thenReturn(employees);
    when(employeeMapper.map(employees)).thenReturn(List.of(employeeDto));
    mvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is(employeeDto.getFirstName())));

    verify(employeeService, times(1)).findAll();
    verify(employeeMapper, times(1)).map(employees);
  }

  @Test
  public void test_get_all_employees_return_empty_json_array_success() throws Exception {
    when(employeeMapper.map(anyList())).thenReturn(Collections.emptyList());
    mvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));

    verify(employeeService, times(1)).findAll();
    verify(employeeMapper, times(1)).map(Collections.emptyList());
  }

  @Test
  public void test_get_employee_return_json_success() throws Exception {
    Long requestedId = 1L;
    Employee employee = new Employee("Bob", "Dylan", LocalDate.now(), EmployeeState.ADDED, null);
    EmployeeDto employeeDto =
        new EmployeeDto("Bob", "Dylan", LocalDate.now(), EmployeeState.ADDED, null);

    when(employeeService.findById(requestedId)).thenReturn(employee);
    when(employeeMapper.map(employee)).thenReturn(employeeDto);
    mvc.perform(get("/employees/{id}", requestedId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(employeeDto.getFirstName())));

    verify(employeeService, times(1)).findById(requestedId);
    verify(employeeMapper, times(1)).map(employee);
  }

  @Test
  public void test_get_unknown_employee_return_empty_json_success() throws Exception {
    Long idOfUnknownEmployee = -1L;

    when(employeeService.findById(idOfUnknownEmployee)).thenThrow(NoSuchElementException.class);
    mvc.perform(get("/employees/{id}", idOfUnknownEmployee).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(employeeService, times(1)).findById(idOfUnknownEmployee);
    verify(employeeMapper, never()).map(any(Employee.class));
  }

  @Test
  public void test_check_employee_success() throws Exception {
    Long id = 1L;

    mvc.perform(put("/employees/{id}/check", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    verify(kafkaProducer, times(1)).send(employeeForCheckTopic, id);
  }

  @Test
  public void test_approve_employee_success() throws Exception {
    Long id = 1L;

    mvc.perform(put("/employees/{id}/approve", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    verify(kafkaProducer, times(1)).send(employeesForApprovalTopic, id);
  }

  @Test
  public void test_activate_employee_success() throws Exception {
    Long id = 1L;

    mvc.perform(put("/employees/{id}/activate", id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());

    verify(kafkaProducer, times(1)).send(employeesForActivationTopic, id);
  }
}
