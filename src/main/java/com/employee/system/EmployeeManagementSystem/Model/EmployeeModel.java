package com.employee.system.EmployeeManagementSystem.Model;

import com.employee.system.EmployeeManagementSystem.Entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("salary")
    private BigDecimal salary;

    @JsonProperty("address")
    private String address;

    @JsonProperty("role")
    private String role;

    @JsonProperty("joining_date")
    private LocalDate joiningDate;

    @JsonProperty("reporting_manager")
    private String reportingManager;

}
