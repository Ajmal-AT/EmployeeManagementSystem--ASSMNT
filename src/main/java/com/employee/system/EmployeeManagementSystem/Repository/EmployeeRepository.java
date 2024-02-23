package com.employee.system.EmployeeManagementSystem.Repository;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByRole(String role, Pageable pageable);

    Page<Employee> findAllByDepartment(Long departmentId, Pageable pageable);
}
