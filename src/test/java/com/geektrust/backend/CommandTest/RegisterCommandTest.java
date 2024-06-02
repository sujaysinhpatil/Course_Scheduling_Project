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

import com.geektrust.backend.Commands.RegisterCommand;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.service.IRegistrationService;

public class RegisterCommandTest {
    
    @InjectMocks
    private RegisterCommand registerCommand;

    @Mock
    private IRegistrationService iRegistrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecuteWithValidInput() {
        List<String> tokens = Arrays.asList("REGISTER_COURSE", "EMAIL_ID", "COURSE_ID");
        RegistrationDto registrationDto = new RegistrationDto("EMAIL_ID", "COURSE_ID");
        when(iRegistrationService.create(any(RegistrationDto.class))).thenReturn("REGISTRATION_ID");

        registerCommand.execute(tokens);

        verify(iRegistrationService).create(registrationDto);
    }

    @Test
    public void testExecuteWithInvalidInputSize() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        List<String> tokens = Arrays.asList("REGISTER_COURSE", "EMAIL_ID");
        String expectedOutput = "INPUT_DATA_ERROR";
        registerCommand.execute(tokens);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

}
