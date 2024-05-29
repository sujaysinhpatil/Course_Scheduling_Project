package com.geektrust.backend.Commands;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.utils.Constant;

public class AllotCommand implements ICommand{

    private final ICourseService iCourseService;
    
    public AllotCommand(ICourseService iCourseService) {
        this.iCourseService = iCourseService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            if(tokens.size()==Constant.EXPECTED_ARGS_ALLOT){
                List<AllotResponse> allotResponses = iCourseService.allotCourse(tokens.get(1)).stream().sorted(Comparator.comparing(AllotResponse::getEmailId)).collect(Collectors.toList());
                for(AllotResponse allotResponse:allotResponses){
                    System.out.println(allotResponse.toString());
                }
            }else{
                throw new InvalidInputException("INPUT_DATA_ERROR");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
