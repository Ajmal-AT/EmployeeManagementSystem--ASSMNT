package com.employee.system.EmployeeManagementSystem.Repository;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String role);

}
