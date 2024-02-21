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

        BigDecimal bonusPercentageForManager = new BigDecimal("0.10");
        BigDecimal bonusPercentageForEmployee = new BigDecimal("0.07");
        BigDecimal defaultBonusPercentage = new BigDecimal("0.04");

        switch(role.toLowerCase()) {
            case "manager":
            case "ceo":
            case "hr manager":
                bonusPercentage = salary.multiply(bonusPercentageForManager);
                break;
            case "jr software developer":
            case "sr software developer":
            case "front end developer":
            case "software testing":
            case "system engineer":
            case "software developer intern":
            case "accountant":
            case "hr assistant":
            case "sales and marketing teams":
                bonusPercentage = salary.multiply(bonusPercentageForEmployee);
                break;
            default:
                bonusPercentage = salary.multiply(defaultBonusPercentage);
                break;
        }

        return bonusPercentage;
    }

    private void setDepartmentAndReportingManager(Employee employee, EmployeeModel employeeModel) {
        if (employeeModel.getRole() != null) {
            String role = setDepartment(employeeModel.getRole());
            Department department = departmentRepository.findByName(role);
            employee.setDepartment(department);
        }

        if (employeeModel.getReportingManager() != null) {
            Employee employee1 = employeeRepository.findById(employeeModel.getReportingManager())
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeModel.getReportingManager()));
            employee.setReportingManager(employee1);
        }
    }

    private String setDepartment(String role) {
        String roles;
        switch (role.toLowerCase()) {
            case "jr software developer":
            case "sr software developer":
            case "front end developer":
            case "software developer intern":
                roles = "Software Development";
                break;
            case "software testing":
                roles = "Quality Assurance";
                break;
            case "ceo":
                roles = "Executive";
                break;
            case "hr manager":
            case "hr assistant":
                roles = "Human Resources";
                break;
            case "accountant":
                roles = "Finance";
                break;
            case "system engineer":
                roles = "Engineering";
                break;
            case "sales and marketing teams":
                roles = "Sales and Marketing";
                break;
            default:
                roles = "employee";
                break;
        }

        return roles;
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
