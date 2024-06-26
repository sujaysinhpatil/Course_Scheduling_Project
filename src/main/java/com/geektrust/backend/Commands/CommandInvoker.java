package com.geektrust.backend.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.geektrust.backend.Exception.InvalidInputException;

public class CommandInvoker {

    private static final Map<String, ICommand> commandMap = new HashMap<>();

    // Register the command into the HashMap
    public void register(String commandName, ICommand command) {
        commandMap.put(commandName,command);
    }

    // Get the registered Command
    private ICommand get(String commandName) {
        return commandMap.get(commandName);
    }

    // Execute the registered Command
    public void executeCommand(String commandName, List<String> tokens) throws InvalidInputException {
        ICommand command = get(commandName);
        if(command == null) {
            // Handle Exception
            throw new InvalidInputException("No command found with name "+commandName);
        }
        command.execute(tokens);
    }

}
