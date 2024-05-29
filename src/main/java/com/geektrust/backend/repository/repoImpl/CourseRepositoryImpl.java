package com.geektrust.backend.repository.repoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.repository.ICourseRepository;

public class CourseRepositoryImpl implements ICourseRepository {

    private final HashMap<String, Course> courseMap;

    public CourseRepositoryImpl() {
        courseMap = new HashMap<>();
    }

    public CourseRepositoryImpl(HashMap<String, Course> courseMap) {
        this.courseMap = courseMap;
    }

    @Override
    public String save(CourseDto courseDto) {
        // OFFERING-<COURSE-NAME>-<INSTRUCTOR>
        courseDto.setCourseId("OFFERING-"+courseDto.getCourseName()+"-"+courseDto.getInstructor());
        Course course = getCourse(courseDto);
        courseMap.put(course.getCourseId(), course);
        return course.getCourseId();
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        Course course = getCourse(courseDto);
        courseMap.put(course.getCourseId(), course);
    }

    @Override
    public List<CourseDto> findAll() {
        return courseMap.values().stream().map(course->getCourseDto(course)).collect(Collectors.toList());
    }

    @Override
    public Optional<CourseDto> findById(String id) {
        CourseDto courseDto = getCourseDto(courseMap.get(id));
        return Optional.ofNullable(courseDto);
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public void delete(CourseDto courseDto) {
        courseMap.entrySet().removeIf(e->e.getValue().equals(getCourse(courseDto)));
    }

    @Override
    public void deleteById(String id) {
        courseMap.remove(id);
    }

    @Override
    public long count() {
        return courseMap.size();
    }

    @Override
    public CourseDto getCourseDto(Course c) {
        CourseDto courseDto = new CourseDto(c.getCourseId(), c.getCourseName(), c.getInstructor(), c.getDate(), c.getMinStrength(), c.getMaxStrength(), c.getStatus(), c.getEmployeeList());
        return courseDto;
    }

    @Override
    public Course getCourse(CourseDto cd) {
        Course course = new Course(cd.getCourseId(), cd.getCourseName(), cd.getInstructor(), cd.getDate(), cd.getMinStrength(), cd.getMaxStrength(), cd.getStatus(), cd.getEmployeeList());
        return course;
    }
    
}
