package com.geektrust.backend.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.repository.repoImpl.EmployeeRepositoryImpl;

public class EmployeeRepositoryTest {

    private EmployeeRepositoryImpl employeeRepository;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    public void setUp() {
        employeeRepository = new EmployeeRepositoryImpl();
        employeeDto1 = new EmployeeDto("John Doe", "john@example.com");
        employeeDto2 = new EmployeeDto("Jane Smith", "jane@example.com");
    }

    @Test
    public void testSave() {
        String email = employeeRepository.save(employeeDto1);
        assertNotNull(email);
        assertEquals("john@example.com", email);
    }

    @Test
    public void testFindAll() {
        employeeRepository.save(employeeDto1);
        employeeRepository.save(employeeDto2);
        List<EmployeeDto> employees = employeeRepository.findAll();
        assertEquals(2, employees.size());
    }

    @Test
    public void testFindById() {
        employeeRepository.save(employeeDto1);
        Optional<EmployeeDto> foundEmployee = employeeRepository.findById(employeeDto1.getEmail());
        assertTrue(foundEmployee.isPresent());
        assertEquals(employeeDto1.getEmail(), foundEmployee.get().getEmail());
    }

    @Test
    public void testExistsById() {
        employeeRepository.save(employeeDto1);
        assertTrue(employeeRepository.existsById(employeeDto1.getEmail()));
        assertFalse(employeeRepository.existsById("non-existent-email@example.com"));
    }

    @Test
    public void testDelete() {
        employeeRepository.save(employeeDto1);
        employeeRepository.delete(employeeDto1);
        assertFalse(employeeRepository.existsById(employeeDto1.getEmail()));
    }

    @Test
    public void testDeleteById() {
        employeeRepository.save(employeeDto1);
        employeeRepository.deleteById(employeeDto1.getEmail());
        assertFalse(employeeRepository.existsById(employeeDto1.getEmail()));
    }

    @Test
    public void testCount() {
        employeeRepository.save(employeeDto1);
        employeeRepository.save(employeeDto2);
        assertEquals(2, employeeRepository.count());
    }

    @Test
    public void testGetEmployeeDto() {
        Employee employee = new Employee("john@example.com");
        EmployeeDto employeeDto = employeeRepository.getEmployeeDto(employee);
        assertEquals(employee.getEmail(), employeeDto.getEmail());
    }

    @Test
    public void testGetEmployee() {
        Employee employee = employeeRepository.getEmployee(employeeDto1);
        assertEquals(employeeDto1.getEmail(), employee.getEmail());
    }
    
}
