package com.employee.system.EmployeeManagementSystem.Controller;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;
import com.employee.system.EmployeeManagementSystem.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeModel employeeModel) {
        Employee createdEmployee = employeeService.addEmployee(employeeModel);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeModel employeeModel, @PathVariable Long id) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeModel, id);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.UPGRADE_REQUIRED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.FOUND);
    }

    @GetMapping("/{role}")
    public ResponseEntity<List<Employee>> getEmployeeById(@PathVariable String role) {
        List<Employee> employees = employeeService.getAllByRole(role);
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
    }
}
