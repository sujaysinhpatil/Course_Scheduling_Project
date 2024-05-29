package com.geektrust.backend.service;

import java.util.List;

import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.dto.CourseDto;

public interface ICourseService {
    String createCourse(CourseDto course);
    CourseDto getCourse(String courseId);
    List<AllotResponse> allotCourse(String string);
}
