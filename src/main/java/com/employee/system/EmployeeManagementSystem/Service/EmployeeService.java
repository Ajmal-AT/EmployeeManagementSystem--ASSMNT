package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Exception.DepartmentNotFoundException;
import com.employee.system.EmployeeManagementSystem.Exception.EmployeeNotFoundException;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;
import com.employee.system.EmployeeManagementSystem.Repository.DepartmentRepository;
import com.employee.system.EmployeeManagementSystem.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceIMPL{

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ModelMapper modelMapper;

    public BigDecimal calculateYearlyBonusPercentage(String role, BigDecimal salary) {
        BigDecimal bonusPercentage = BigDecimal.ZERO;

        BigDecimal salary1 = salary;
        String role1 = role;

        BigDecimal bonusPercentageForManager = new BigDecimal("0.10");
        BigDecimal bonusPercentageForEmployee = new BigDecimal("0.05");

        if ("Manager".equalsIgnoreCase(role)) {
            bonusPercentage = salary.multiply(bonusPercentageForManager);
        } else {
            bonusPercentage = salary.multiply(bonusPercentageForEmployee);
        }

        return bonusPercentage;
    }

    private void setDepartmentAndReportingManager(Employee employee, EmployeeModel employeeModel) {
        if (employeeModel.getDepartment() != null) {
            Department department = departmentRepository.findById(employeeModel.getDepartment())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + employeeModel.getDepartment()));
            employee.setDepartment(department);
        }

        if (employeeModel.getReportingManager() != null) {
            Employee employee1 = employeeRepository.findById(employeeModel.getReportingManager())
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeModel.getReportingManager()));
            employee.setReportingManager(employee1);
        }
    }

    @Override
    public Employee addEmployee(EmployeeModel employeeModel) {
        Employee employee = modelMapper.map(employeeModel, Employee.class);
        employee.setYearlyBonusPercentage(calculateYearlyBonusPercentage(employeeModel.getRole(), employeeModel.getSalary()));
        setDepartmentAndReportingManager(employee, employeeModel);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Sorry, this Employee could not be found with id: "+ id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(EmployeeModel employeeModel, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        if (employeeModel.getRole() == null || employeeModel.getSalary() == null ){
            Employee employee1 = modelMapper.map(employeeModel, Employee.class);
            setDepartmentAndReportingManager(employee1, employeeModel);
            return employeeRepository.save(employee1);
        }
        else {
            Employee employee1 = modelMapper.map(employeeModel, Employee.class);
            setDepartmentAndReportingManager(employee1, employeeModel);
            employee1.setYearlyBonusPercentage(calculateYearlyBonusPercentage(employeeModel.getRole(), employeeModel.getSalary()));
            return employeeRepository.save(employee1);
        }
    }

    @Override
    public String deleteEmployee(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException("Sorry, this Employee could not be found in the id ="+ id);
        }
        employeeRepository.deleteById(id);
        return "Successfully Deleted";
    }

    @Override
    public List<Employee> getAllByRole(String Role) {
        List<Employee> employees = employeeRepository.findAllByRole(Role);
        if(employees == null){
            throw new EmployeeNotFoundException("Sorry, this Employee could not be found in the Role = "+ Role);
        }
        return employees;
    }
}
