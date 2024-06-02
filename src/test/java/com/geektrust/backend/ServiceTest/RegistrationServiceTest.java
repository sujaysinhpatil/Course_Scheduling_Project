package com.geektrust.backend.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Exception.CourseException;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.service.IEmployeeService;
import com.geektrust.backend.service.serviceImpl.RegistrationServiceImpl;

public class RegistrationServiceTest {
    
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private IEmployeeService employeeService;

    @Mock
    private ICourseService courseService;

    private RegistrationDto registrationDto;
    private EmployeeDto employeeDto;
    private CourseDto courseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        registrationDto = new RegistrationDto("REG1", "john@example.com", "COURSE1", false);
        employeeDto = new EmployeeDto("John Doe", "john@example.com");
        courseDto = new CourseDto("COURSE1", "Course Name", "Instructor", "2024-06-20", 2, 3, "OFFERING", new ArrayList<>());
    }

    @Test
    public void testCreate() {
        when(employeeService.existsById("john@example.com")).thenReturn(true);
        when(employeeService.getEmployee("john@example.com")).thenReturn(employeeDto);
        when(courseService.getCourse("COURSE1")).thenReturn(courseDto);
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(new ArrayList<>());
        when(registrationRepository.save(any(RegistrationDto.class))).thenReturn("REG-COURSE-John Doe-Course Name");

        String regId = registrationService.create(registrationDto);

        assertEquals("REG-COURSE-John Doe-Course Name", regId);
        verify(registrationRepository).save(any(RegistrationDto.class));
    }

    @Test
    public void testCreateCourseFull() {
        courseDto = new CourseDto("COURSE1", "Course Name", "Instructor", "2024-06-20", 1, 1, "OFFERING", new ArrayList<>());
        when(employeeService.existsById("john@example.com")).thenReturn(true);
        when(employeeService.getEmployee("john@example.com")).thenReturn(employeeDto);
        when(courseService.getCourse("COURSE1")).thenReturn(courseDto);
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(Arrays.asList(registrationDto));

        CourseException exception = assertThrows(CourseException.class, () -> {
            registrationService.create(registrationDto);
        });

        assertEquals("COURSE_FULL_ERROR", exception.getMessage());
    }

    @Test
    public void testCreateCourseAlreadyAlloted() {
        courseDto.setStatus("CONFIRMED");
        when(employeeService.existsById("john@example.com")).thenReturn(true);
        when(employeeService.getEmployee("john@example.com")).thenReturn(employeeDto);
        when(courseService.getCourse("COURSE1")).thenReturn(courseDto);
        when(registrationRepository.findAllByCourseId("COURSE1")).thenReturn(new ArrayList<>());

        CourseException exception = assertThrows(CourseException.class, () -> {
            registrationService.create(registrationDto);
        });

        assertEquals("COURSE_ALREADY_ALLOTED", exception.getMessage());
    }

    @Test
    public void testCancelRegistrationAccepted() {
        registrationDto.setAccepted(true);
        when(registrationRepository.findById("REG1")).thenReturn(Optional.of(registrationDto));
        when(courseService.getCourse("COURSE1")).thenReturn(courseDto);

        String result = registrationService.cancelRegistration("REG1");

        assertEquals("REG1 CANCEL_ACCEPTED", result);
        verify(registrationRepository).deleteById("REG1");
    }

    @Test
    public void testCancelRegistrationRejected() {
        registrationDto.setAccepted(true);
        courseDto.setStatus("CONFIRMED");
        when(registrationRepository.findById("REG1")).thenReturn(Optional.of(registrationDto));
        when(courseService.getCourse("COURSE1")).thenReturn(courseDto);

        String result = registrationService.cancelRegistration("REG1");

        assertEquals("REG1 CANCEL_REJECTED", result);
        verify(registrationRepository, never()).deleteById("REG1");
    }

}
