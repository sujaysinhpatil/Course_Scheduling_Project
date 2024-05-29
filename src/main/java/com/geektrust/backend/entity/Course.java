package com.geektrust.backend.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.Setter;

@Data
// @Setter
@AllArgsConstructor
public class Course {
    private String courseId;
    private final String courseName;
    private final String instructor;
    private final String date;
    private final int minStrength;
    private final int maxStrength;
    private String status;
    private List<Employee> employeeList;
}
