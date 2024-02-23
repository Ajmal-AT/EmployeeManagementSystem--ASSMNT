package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Exception.DepartmentNotFoundException;
import com.employee.system.EmployeeManagementSystem.Exception.EmployeeNotFoundException;
import com.employee.system.EmployeeManagementSystem.Model.DepartmentModel;
import com.employee.system.EmployeeManagementSystem.Repository.DepartmentRepository;
import com.employee.system.EmployeeManagementSystem.Repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements DepartmentServiceIMPL{

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    public Department addDepartment(DepartmentModel departmentModel) {
        String departmentName = departmentModel.getName();
        Long departmentHeadId = departmentModel.getDepartment();

        if (departmentName == null && departmentHeadId == null) {
            throw new DepartmentNotFoundException("Department name and department head ID are required.");
        }

        if (departmentName != null && departmentRepository.existsByName(departmentName)) {
            throw new DepartmentNotFoundException("Department already exists with the name: " + departmentName);
        }

        Employee departmentHead = null;
        if (departmentHeadId != null) {
            departmentHead = employeeRepository.findById(departmentHeadId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + departmentHeadId));
        }

        Department department = new Department();
        department.setName(departmentName);
        department.setDepartmentHead(departmentHead);

        return departmentRepository.save(department);
    }


    @Override
    public String deleteDepartment(Long id) {
        String val = null;
        if(departmentRepository.existsById(id)) {
            Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
            Page<Employee> employees = employeeRepository.findAllByDepartment(id, pageable);
            if(employees.isEmpty()) {
                throw new EmployeeNotFoundException("Employees not found with department :"+ id);
            }
            for (Employee employee : employees) {
                employee.setDepartment(null);
                employeeRepository.save(employee);
            }
            departmentRepository.deleteById(id);
            val = "Department deleted Successfully with the Id"+id;
        }
        return val;
    }

    @Override
    public Department updateDepartment(DepartmentModel departmentModel, Long id) {
        String departmentName = departmentModel.getName();
        Long departmentHeadId = departmentModel.getDepartment();

        if (departmentName == null && departmentHeadId == null) {
            throw new DepartmentNotFoundException("Department name or department head ID is required.");
        }

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        if (departmentName != null) {
            Department existingDepartment = departmentRepository.findByNameAndDepartmentHead(departmentName, departmentHeadId);
            if (existingDepartment == null && existingDepartment.getId().equals(id)) {
                throw new DepartmentNotFoundException("Department not exist");
            }

            department.setName(departmentName);

            if (departmentHeadId != null) {
                Employee departmentHead = employeeRepository.findById(departmentHeadId)
                        .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + departmentHeadId));
                department.setDepartmentHead(departmentHead);
            }
        }

        return departmentRepository.save(department);
    }

    @Override
    public Employee moveEmployeeToAnotherDepartment(Long employeeId, Long newDepartmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));
        Department department = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: "+newDepartmentId));

        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesUnderDepartment(Long departmentId) {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        Page<Employee> employees = employeeRepository.findAllByDepartment(departmentId, pageable);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found under department with ID: " + departmentId);
        }
        return employees.getContent();
    }

}
