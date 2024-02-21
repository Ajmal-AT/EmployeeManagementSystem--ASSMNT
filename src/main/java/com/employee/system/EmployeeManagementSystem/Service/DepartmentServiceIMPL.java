package com.employee.system.EmployeeManagementSystem.Service;

import com.employee.system.EmployeeManagementSystem.Entity.Department;
import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.employee.system.EmployeeManagementSystem.Model.DepartmentModel;

import java.util.List;

public interface DepartmentServiceIMPL {

    public Department addDepartment(DepartmentModel departmentModel);

    public String deleteDepartment(Long id);

    public Department updateDepartment(DepartmentModel departmentModel, Long id);

    public Employee moveEmployeeToAnotherDepartment(Long employeeId, Long newDepartmentId);

    public List<Department> getAllDepartments();

    public List<Employee> getEmployeesUnderDepartment(Long departmentId);

}
