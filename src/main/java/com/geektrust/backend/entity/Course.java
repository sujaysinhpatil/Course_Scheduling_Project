package com.geektrust.backend.entity;

import java.util.List;
import com.geektrust.backend.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private String courseId;
    private final String courseName;
    private final String instructor;
    private final String date;
    private final int minStrength;
    private final int maxStrength;
    private String status;
    private List<EmployeeDto> employeeList;
}
