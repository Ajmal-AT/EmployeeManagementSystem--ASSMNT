package com.employee.system.EmployeeManagementSystem.Controller;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;
import com.employee.system.EmployeeManagementSystem.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestBody EmployeeModel employeeModel)  {
        return new ResponseEntity<>(employeeService.getAllEmployees(employeeModel), HttpStatus.FOUND);
    }
}
