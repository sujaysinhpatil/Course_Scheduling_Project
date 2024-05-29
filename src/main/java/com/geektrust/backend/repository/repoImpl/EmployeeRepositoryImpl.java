package com.geektrust.backend.repository.repoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.repository.IEmployeeRepository;

public class EmployeeRepositoryImpl implements IEmployeeRepository {

    private final HashMap<String,Employee> empMap;

    public EmployeeRepositoryImpl() {
        empMap = new HashMap<>();
    }

    public EmployeeRepositoryImpl(HashMap<String,Employee> empMap) {
        this.empMap = empMap;
    }

    @Override
    public String save(EmployeeDto employeeDto) {
        Employee emp = new Employee(employeeDto.getEmail());
        empMap.put(emp.getEmail(),emp);
        return emp.getEmail();
    }

    @Override
    public List<EmployeeDto> findAll() {
        return empMap.values().stream().map(e->getEmployeeDto(e)).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDto> findById(String id) {
        return Optional.of(getEmployeeDto(empMap.get(id)));
    }

    @Override
    public boolean existsById(String id) {
        return empMap.containsKey(id);
    }

    @Override
    public void delete(EmployeeDto employeeDto) {
        empMap.entrySet().removeIf(a -> a.getValue().equals(getEmployee(employeeDto))); 
    }

    @Override
    public void deleteById(String id) {
        empMap.remove(id);
    }

    @Override
    public long count() {
        return empMap.size();
    }
    
    @Override
    public EmployeeDto getEmployeeDto(Employee employee){
        return new EmployeeDto(employee.getName(), employee.getEmail());
    }

    @Override
    public Employee getEmployee(EmployeeDto employeeDto){
        return new Employee(employeeDto.getEmail());
    }
}
