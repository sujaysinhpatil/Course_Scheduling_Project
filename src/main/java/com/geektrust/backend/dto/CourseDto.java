package com.geektrust.backend.dto;

import java.util.List;

import com.geektrust.backend.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CourseDto {
    private String courseId;
    private final String courseName;
    private final String instructor;
    private final String date;
    private final int minStrength;
    private final int maxStrength;
    private String status;
    private List<Employee> employeeList;
}
