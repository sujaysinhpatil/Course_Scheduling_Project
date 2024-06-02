package com.geektrust.backend.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.repository.IEmployeeRepository;
import com.geektrust.backend.service.serviceImpl.EmployeeServiceImpl;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private IEmployeeRepository employeeRepository;

    private EmployeeDto employeeDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employeeDto = new EmployeeDto("John Doe", "john@example.com");
    }

    @Test
    public void testGetEmployeeSuccess() {
        when(employeeRepository.findById("john@example.com")).thenReturn(Optional.of(employeeDto));

        EmployeeDto result = employeeService.getEmployee("john@example.com");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testGetEmployeeNotFound() {
        when(employeeRepository.findById("john@example.com")).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            employeeService.getEmployee("john@example.com");
        });

        assertEquals("Employee with id john@example.com doen't exist", exception.getMessage());
    }

    @Test
    public void testExistsById() {
        when(employeeRepository.existsById("john@example.com")).thenReturn(true);

        boolean exists = employeeService.existsById("john@example.com");

        assertTrue(exists);
    }

    @Test
    public void testExistsByIdNotFound() {
        when(employeeRepository.existsById("john@example.com")).thenReturn(false);

        boolean exists = employeeService.existsById("john@example.com");

        assertFalse(exists);
    }

    @Test
    public void testSave() {
        when(employeeRepository.save(employeeDto)).thenReturn("john@example.com");

        String result = employeeService.save(employeeDto);

        assertEquals("john@example.com", result);
    }
    
}
