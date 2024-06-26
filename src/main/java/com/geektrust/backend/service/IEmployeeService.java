package com.geektrust.backend.service;

import com.geektrust.backend.dto.EmployeeDto;

public interface IEmployeeService {
    EmployeeDto getEmployee(String emailId);
    boolean existsById(String id);
    String save(EmployeeDto employeeDto);
}
