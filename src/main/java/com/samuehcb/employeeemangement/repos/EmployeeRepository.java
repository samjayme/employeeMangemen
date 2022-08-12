package com.samuehcb.employeeemangement.repos;

import com.samuehcb.employeeemangement.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
