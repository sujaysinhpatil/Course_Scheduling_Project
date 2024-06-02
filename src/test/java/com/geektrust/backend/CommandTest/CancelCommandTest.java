package com.geektrust.backend.CommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.geektrust.backend.Commands.CancelCommand;
import com.geektrust.backend.service.IRegistrationService;

public class CancelCommandTest {

    @InjectMocks
    private CancelCommand cancelCommand;

    @Mock
    private IRegistrationService iRegistrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecuteWithValidInput() {
        List<String> tokens = Arrays.asList("CANCEL", "REGISTRATION_ID");
        when(iRegistrationService.cancelRegistration("REGISTRATION_ID")).thenReturn("REGISTRATION_ID CANCEL_ACCEPTED");

        cancelCommand.execute(tokens);

        verify(iRegistrationService).cancelRegistration("REGISTRATION_ID");
    }

    @Test
    public void testExecuteWithInvalidInputSize() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        List<String> tokens = Arrays.asList("CANCEL");
        String expectedOutput = "INPUT_DATA_ERROR";
        cancelCommand.execute(tokens);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }
    
}
