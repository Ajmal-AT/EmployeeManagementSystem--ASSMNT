package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Exception.EmployeeNotFoundException;
import com.employee.system.EmployeeManagementSystem.Model.EmployeeModel;
import com.employee.system.EmployeeManagementSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements EmployeeServiceIMPL{

    @Autowired
    EmployeeRepository employeeRepository;



    public BigDecimal calculateYearlyBonusPercentage(EmployeeModel employee) {
        BigDecimal bonusPercentage = BigDecimal.ZERO;

        BigDecimal salary = employee.getSalary();
        String role = employee.getRole();

        BigDecimal bonusPercentageForManager = new BigDecimal("0.10");
        BigDecimal bonusPercentageForEmployee = new BigDecimal("0.05");

        if ("Manager".equalsIgnoreCase(role)) {
            bonusPercentage = salary.multiply(bonusPercentageForManager);
        } else {
            bonusPercentage = salary.multiply(bonusPercentageForEmployee);
        }

        return bonusPercentage;
    }

    @Override
    public Employee addEmployee(EmployeeModel employeeModel) {
        return null;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees(EmployeeModel employeeModel) {
        return null;
    }

    @Override
    public Employee updateEmployee(EmployeeModel employeeModel, Long id) {
        Employee employee = employeeRepository.findById(id).get();

        if(employee == null){
            throw new EmployeeNotFoundException("Sorry, this Employee could not be found");
        }

//        return (Employee) this.employeeRepository.findById(id).map((emp) -> {
//            emp.setName(employeeModel.getName());
//            emp.setAddress(employeeModel.getAddress());
//
//            return (Question)this.questionRepository.save(qu);
//        }).orElseThrow(() -> {
//            return new QuestionNotFoundException("Sorry, this Question could not be found");
//        });
        return  null;
    }

    @Override
    public String deleteEmployee(Long Id) {
        return null;
    }

    @Override
    public List<Employee> getAllByRole(String Role) {
        return null;
    }
}
