package com.employee.system.EmployeeManagementSystem.Model;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("department_head_id")
    private Long department;

}
