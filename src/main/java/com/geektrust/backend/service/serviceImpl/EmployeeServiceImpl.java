package com.geektrust.backend.service.serviceImpl;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.repository.IEmployeeRepository;
import com.geektrust.backend.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeRepository iEmployeeRepository;

    public EmployeeServiceImpl(IEmployeeRepository iEmployeeRepository) {
        this.iEmployeeRepository = iEmployeeRepository;
    }

    @Override
    public EmployeeDto getEmployee(String emailId) {
        return iEmployeeRepository.findById(emailId).orElseThrow(()->new InvalidInputException("Employee with id " +emailId+ " doen't exist"));
    }
    
}
