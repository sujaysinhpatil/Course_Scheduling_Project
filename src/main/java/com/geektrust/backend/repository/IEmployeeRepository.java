package com.geektrust.backend.repository;

import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.entity.Employee;

public interface IEmployeeRepository extends CRUDRepository<EmployeeDto,String> {
    Employee getEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeDto(Employee employee);
}
