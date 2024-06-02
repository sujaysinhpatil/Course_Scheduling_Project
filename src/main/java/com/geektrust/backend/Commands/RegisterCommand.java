package com.geektrust.backend.Commands;

import java.util.List;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.service.IRegistrationService;
import com.geektrust.backend.utils.Constant;

public class RegisterCommand implements ICommand {

    private final IRegistrationService iRegistrationService;

    public RegisterCommand(IRegistrationService registrationSevice) {
        this.iRegistrationService = registrationSevice;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            if(tokens.size()!=Constant.EXPECTED_ARGS_REGISTER_COURSE) {
                throw new InvalidInputException("INPUT_DATA_ERROR");
            }
            RegistrationDto registrationDto = getRegistrationDto(tokens);
            String registrationId = iRegistrationService.create(registrationDto);
            System.out.println(registrationId+" "+"ACCEPTED");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private RegistrationDto getRegistrationDto(List<String> tokens) {
        RegistrationDto registrationDto = new RegistrationDto(tokens.get(1),tokens.get(2));
        return registrationDto;
    }

}
