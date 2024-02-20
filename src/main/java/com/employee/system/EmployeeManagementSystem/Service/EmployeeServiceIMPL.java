package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;

import java.util.List;

public interface EmployeeServiceIMPL {

    Employee addEmployee(EmployeeModel employeeModel);
    
    Employee getEmployeeById(Long id);
    
    List<Employee> getAllEmployees();

    Employee updateEmployee(EmployeeModel employeeModel, Long Id);
    
    String deleteEmployee(Long Id);
    
    List<Employee> getAllByRole(String Role);
}
