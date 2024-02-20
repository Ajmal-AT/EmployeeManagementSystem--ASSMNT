package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.DepartmentModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements DepartmentServiceIMPL{

    @Override
    public Department addDepartment(DepartmentModel departmentModel) {
        return null;
    }

    @Override
    public String deleteDepartment(Long id) {
        return null;
    }

    @Override
    public Department updateDepartment(DepartmentModel departmentModel, Long id) {
        return null;
    }

    @Override
    public Department moveEmployeeToAnotherDepartment(Long employeeId, Long newDepartmentId) {
        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        return null;
    }

    @Override
    public List<Employee> getEmployeesUnderDepartment(Long departmentId) {
        return null;
    }
}
