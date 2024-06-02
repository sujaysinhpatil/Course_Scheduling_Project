package com.geektrust.backend.CommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.geektrust.backend.Commands.AllotCommand;
import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.service.ICourseService;

public class AllotCommandTest {

    @InjectMocks
    private AllotCommand allotCommand;

    @Mock
    private ICourseService iCourseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecuteWithValidInput() {
        List<String> tokens = Arrays.asList("ALLOT", "COURSE_ID");
        List<AllotResponse> allotResponses = Collections.singletonList(new AllotResponse("REG_ID", "EMAIL_ID", "COURSE_ID", "COURSE_NAME", "INSTRUCTOR", "DATE", "STATUS"));

        when(iCourseService.allotCourse("COURSE_ID")).thenReturn(allotResponses);

        allotCommand.execute(tokens);

        verify(iCourseService).allotCourse("COURSE_ID");
    }

    @Test
    public void testExecuteWithInvalidInputSize() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        List<String> tokens = Arrays.asList("ALLOT");
        String expectedOutput = "INPUT_DATA_ERROR";
        allotCommand.execute(tokens);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }
    
}
