package com.employee.system.EmployeeManagementSystem.Controller;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.DepartmentModel;
import com.employee.system.EmployeeManagementSystem.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department> addDepartment(@RequestBody DepartmentModel departmentModel) {
        Department createdDepartment = departmentService.addDepartment(departmentModel);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable Long id) {
        String result = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Department> updateDepartmentById(@RequestBody DepartmentModel departmentModel, @PathVariable Long id) {
        Department updatedDepartment = departmentService.updateDepartment(departmentModel, id);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.UPGRADE_REQUIRED);
    }

    @PutMapping("/{departmentId}/employees/{employeeId}")
    public ResponseEntity<Employee> moveEmployeeToAnotherDepartment(@PathVariable Long employeeId, @PathVariable Long newDepartmentId) {
        Employee employee = departmentService.moveEmployeeToAnotherDepartment(employeeId, newDepartmentId);
        return new ResponseEntity<>(employee, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.FOUND);
    }

    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesUnderDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = departmentService.getEmployeesUnderDepartment(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }
}
