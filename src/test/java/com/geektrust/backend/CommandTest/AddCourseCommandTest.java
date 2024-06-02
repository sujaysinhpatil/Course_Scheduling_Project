package com.geektrust.backend.CommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Commands.AddCourseCommand;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.service.ICourseService;

public class AddCourseCommandTest {

    @InjectMocks
    private AddCourseCommand addCourseCommand;

    @Mock
    private ICourseService iCourseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecuteWithValidInput() {
        List<String> tokens = Arrays.asList("ADD_COURSE", "COURSE_NAME", "INSTRUCTOR", "2024-06-01", "2", "10");
        CourseDto courseDto = new CourseDto("COURSE_NAME", "INSTRUCTOR", "2024-06-01", 2, 10);
        when(iCourseService.createCourse(courseDto)).thenReturn("OFFERING-COURSE_NAME-INSTRUCTOR");

        addCourseCommand.execute(tokens);

        verify(iCourseService).createCourse(any(CourseDto.class));
    }

    @Test
    public void testExecuteWithInvalidInputSize() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        List<String> tokens = Arrays.asList("ADD_COURSE", "COURSE_NAME", "INSTRUCTOR");
        String expectedOutput = "INPUT_DATA_ERROR";
        addCourseCommand.execute(tokens);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testExecuteWithNumberFormatException() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        List<String> tokens = Arrays.asList("ADD_COURSE", "COURSE_NAME", "INSTRUCTOR", "2024-06-01", "two", "10");
        String expectedOutput = "For input string: \"two\"";
        addCourseCommand.execute(tokens);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testGetCourseDto() {
        List<String> tokens = Arrays.asList("ADD_COURSE", "COURSE_NAME", "INSTRUCTOR", "2024-06-01", "2", "10");
        CourseDto courseDto = addCourseCommand.getCourseDto(tokens);

        assertEquals("COURSE_NAME", courseDto.getCourseName());
        assertEquals("INSTRUCTOR", courseDto.getInstructor());
        assertEquals("2024-06-01", courseDto.getDate());
        assertEquals(2, courseDto.getMinStrength());
        assertEquals(10, courseDto.getMaxStrength());
    }
    
}
