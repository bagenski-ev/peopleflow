package by.bagenskij.peopleflow.dao.repository;

import by.bagenskij.peopleflow.dao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
