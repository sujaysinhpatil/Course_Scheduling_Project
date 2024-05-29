package com.geektrust.backend.repository;

import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.entity.Course;

public interface ICourseRepository extends CRUDRepository<CourseDto, String> {
    public void updateCourse(CourseDto courseDto);
    public CourseDto getCourseDto(Course course);
    public Course getCourse(CourseDto courseDto);
}
