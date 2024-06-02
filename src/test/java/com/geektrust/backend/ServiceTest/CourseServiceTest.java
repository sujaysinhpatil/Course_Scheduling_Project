package com.geektrust.backend.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

import com.geektrust.backend.Exception.CourseException;
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
        when(courseRepository.save(courseDto)).thenReturn("COURSE1");
        String courseId = courseService.createCourse(courseDto);
        assertEquals("COURSE1", courseId);
    }

    @Test
    public void testAllotCourseSuccessful() {
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.of(courseDto));
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(Arrays.asList(registrationDto1, registrationDto2));
        when(employeeService.getEmployee("john@example.com")).thenReturn(employeeDto1);
        when(employeeService.getEmployee("jane@example.com")).thenReturn(employeeDto2);

        List<AllotResponse> responses = courseService.allotCourse("COURSE1");

        assertEquals(2, responses.size());
        verify(courseRepository).updateCourse(any(CourseDto.class));
    }

    @Test
    public void testAllotCourseFailure() {
        // Set minimum strength greater than available registrations
        courseDto = new CourseDto("COURSE1", "Course Name", "Instructor", "2024-06-20", 3, 10, "OFFERING", new ArrayList<>());
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.of(courseDto));
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(Arrays.asList(registrationDto1, registrationDto2));

        CourseException exception = assertThrows(CourseException.class, () -> {
            courseService.allotCourse("COURSE1");
        });

        assertEquals("COURSE_CANCELED", exception.getMessage());
    }

    @Test
    public void testGetCourse() {
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.of(courseDto));
        CourseDto retrievedCourse = courseService.getCourse("COURSE1");
        assertEquals("COURSE1", retrievedCourse.getCourseId());
    }

    @Test
    public void testGetCourseNotFound() {
        when(courseRepository.findById("COURSE1")).thenReturn(Optional.empty());
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            courseService.getCourse("COURSE1");
        });

        assertEquals("Course with id COURSE1 doen't exist", exception.getMessage());
    }

    @Test
    public void testGetAllotResponse() {
        AllotResponse response = courseService.getAllotResponse(courseDto, registrationDto1);
        assertEquals("REG1", response.getRegId());
        assertEquals("COURSE1", response.getCourseId());
    }
    
}
