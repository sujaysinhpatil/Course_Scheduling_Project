package com.geektrust.backend.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.service.IEmployeeService;
import com.geektrust.backend.service.serviceImpl.CourseServiceImpl;

public class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private IEmployeeService employeeService;

    private CourseDto courseDto;
    private RegistrationDto registrationDto1;
    private RegistrationDto registrationDto2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        courseDto = new CourseDto("COURSE1", "Course Name", "Instructor", "2024-06-20", 2, 10, "OFFERING", new ArrayList<>());
        registrationDto1 = new RegistrationDto("REG1", "john@example.com", "COURSE1", true);
        registrationDto2 = new RegistrationDto("REG2", "jane@example.com", "COURSE1", true);
        employeeDto1 = new EmployeeDto("John Doe", "john@example.com");
        employeeDto2 = new EmployeeDto("Jane Smith", "jane@example.com");
    }

    @Test
    public void testCreateCourse() {
        when(courseRepository.save(any(CourseDto.class))).thenReturn("OFFERING-COURSE1-INSTRUCTOR");

        String result = courseService.createCourse(courseDto);

        assertEquals("OFFERING-COURSE1-INSTRUCTOR", result);
    }

    @Test
    public void testAllotCourse() {
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.of(courseDto));
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(Arrays.asList(registrationDto1, registrationDto2));
        when(employeeService.getEmployee("john@example.com")).thenReturn(employeeDto1);
        when(employeeService.getEmployee("jane@example.com")).thenReturn(employeeDto2);

        List<AllotResponse> responses = courseService.allotCourse("COURSE1");

        assertEquals(2, responses.size());
        assertEquals("john@example.com", responses.get(0).getEmailId());
        assertEquals("jane@example.com", responses.get(1).getEmailId());
    }

    @Test
    public void testAllotCourseThrowsException() {
        String courseId = "INVALID_COURSE_ID";
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            courseService.allotCourse(courseId);
        });

        assertEquals("Course not found with id " + courseId, exception.getMessage());
    }

    @Test
    public void testGetCourse() {
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.of(courseDto));

        CourseDto result = courseService.getCourse("COURSE1");

        assertEquals("COURSE1", result.getCourseId());
    }

    @Test
    public void testGetCourseThrowsException() {
        String courseId = "INVALID_COURSE_ID";
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            courseService.getCourse(courseId);
        });

        assertEquals("Course with id " + courseId + " doen't exist", exception.getMessage());
    }
    
}
