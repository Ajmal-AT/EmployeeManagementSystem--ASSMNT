package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeServiceIMPL {

    Employee addEmployee(EmployeeModel employeeModel);
    
    Optional<Employee> getEmployeeById(Long id);
    
    List<Employee> getAllEmployees(EmployeeModel employeeModel);

    Employee updateEmployee(EmployeeModel employeeModel, Long Id);
    
    String deleteEmployee(Long Id);
    
    List<Employee> getAllByRole(String Role);
}
