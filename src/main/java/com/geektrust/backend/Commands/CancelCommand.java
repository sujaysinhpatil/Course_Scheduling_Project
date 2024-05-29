package com.geektrust.backend.Commands;

import java.util.List;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.service.IRegistrationService;
import com.geektrust.backend.utils.Constant;

public class CancelCommand implements ICommand{

    private final IRegistrationService iRegistrationService;

    public CancelCommand(IRegistrationService iRegistrationService) {
        this.iRegistrationService = iRegistrationService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            if(tokens.size()!=Constant.EXPECTED_ARGS_CANCEL){
                throw new InvalidInputException("INPUT_DATA_ERROR");
            }else{
                String regid = iRegistrationService.cancelRegistration(tokens.get(1));
                System.out.println(regid+" "+"CANCEL_ACCEPTED");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());            
        }
    }

}
