package com.geektrust.backend.Commands;

// import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.utils.Constant;

public class AddCourseCommand implements ICommand{
    
    private final ICourseService iCourseService;

    public AddCourseCommand(ICourseService iCourseService){
        this.iCourseService = iCourseService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            if(tokens.size()!=Constant.EXPECTED_ARGS_ADD_COURSE){
                throw new InvalidInputException("INPUT_DATA_ERROR");
            }

            CourseDto courseDto = getCourseDto(tokens);
            String courseId = iCourseService.createCourse(courseDto);

            System.out.println(courseId);
        } catch(NumberFormatException e){
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        } 
            
    }

    public CourseDto getCourseDto(List<String> tokens){        
        // CourseDto courseDto = new CourseDto(tokens.get(1), tokens.get(2), tokens.get(3), Integer.parseInt(tokens.get(4)), Integer.parseInt(tokens.get(5)), false, false, new ArrayList<>());
        CourseDto courseDto = new CourseDto(tokens.get(1), tokens.get(2), tokens.get(3), Integer.parseInt(tokens.get(4)), Integer.parseInt(tokens.get(5)));
        // CourseDto courseDto1 = new CourseDto(null, null, null, null, 0, 0, null, new ArrayList<>());
        return courseDto;                   
    }

}
