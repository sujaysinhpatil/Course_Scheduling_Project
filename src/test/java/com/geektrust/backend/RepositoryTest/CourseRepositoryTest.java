package com.geektrust.backend.RepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.repository.repoImpl.CourseRepositoryImpl;

public class CourseRepositoryTest {
    
    private CourseRepositoryImpl courseRepository;
    private CourseDto courseDto;
    private CourseDto courseDto2;

    @BeforeEach
    void setUp() {
        courseRepository = new CourseRepositoryImpl();
        courseDto = new CourseDto("JAVA", "JOHN", "2024-06-20", 1, 2);
        courseDto2 = new CourseDto("PYTHON", "JANE", "2024-06-25", 1, 3);
    }

    @Test
    void testSave() {
        String savedId = courseRepository.save(courseDto);
        assertEquals(courseDto.getCourseId(), savedId); // Verify saved ID
        assertTrue(courseRepository.existsById(savedId)); // Verify existence
    }

    @Test
    void testUpdateCourse() {
        courseRepository.save(courseDto);
        courseDto.setStatus("CONFIRMED"); // Modify the course
        courseRepository.updateCourse(courseDto);
        Optional<CourseDto> updatedCourseOptional = courseRepository.findById(courseDto.getCourseId());

        assertTrue(updatedCourseOptional.isPresent());
        assertEquals("CONFIRMED", updatedCourseOptional.get().getStatus());
    }

    @Test
    void testFindAll() {
        courseRepository.save(courseDto);
        courseRepository.save(courseDto2);
        List<CourseDto> courses = courseRepository.findAll();
        assertEquals(2, courses.size()); // Verify both courses are retrieved
        assertTrue(courses.contains(courseDto)); // Verify specific courses
        assertTrue(courses.contains(courseDto2));
    }

    @Test
    void testFindById() {
        courseRepository.save(courseDto);
        Optional<CourseDto> foundCourse = courseRepository.findById(courseDto.getCourseId());
        assertTrue(foundCourse.isPresent());
        assertEquals(courseDto, foundCourse.get());
    }

    @Test
    void testExistsById() {
        courseRepository.save(courseDto);
        assertTrue(courseRepository.existsById(courseDto.getCourseId()));
        assertFalse(courseRepository.existsById("NON_EXISTENT_ID"));
    }

    @Test
    void testDelete() {
        courseRepository.save(courseDto);
        courseRepository.delete(courseDto);
        assertFalse(courseRepository.existsById(courseDto.getCourseId()));
    }

    @Test
    void testDeleteById() {
        courseRepository.save(courseDto);
        courseRepository.deleteById(courseDto.getCourseId());
        assertFalse(courseRepository.existsById(courseDto.getCourseId()));
    }

    @Test
    void testCount() {
        assertEquals(0, courseRepository.count());
        courseRepository.save(courseDto);
        assertEquals(1, courseRepository.count()); 
    }

    @Test
    public void testGetCourseDto() {
        Course course = new Course("OFFERING-Java-John", "Java", "John", "2024-01-20", 5, 10, "YET_TO_START", new ArrayList<>());
        CourseDto courseDto = courseRepository.getCourseDto(course);
        assertEquals(course.getCourseId(), courseDto.getCourseId());
        assertEquals(course.getCourseName(), courseDto.getCourseName());
    }

    @Test
    public void testGetCourse() {
        courseRepository.save(courseDto);
        Course course = courseRepository.getCourse(courseDto);
        assertEquals(courseDto.getCourseId(), course.getCourseId());
        assertEquals(courseDto.getCourseName(), course.getCourseName());
    }
    
}
