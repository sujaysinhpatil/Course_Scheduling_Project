package com.geektrust.backend.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.service.ICourseService;

public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository iCourseRepository;
    private final IRegistrationRepository iRegistrationRepository;
    // private final ICourseRepository iCourseRepository;

    public CourseServiceImpl(ICourseRepository iCourseRepository, IRegistrationRepository iRegistrationRepository) {
        this.iCourseRepository = iCourseRepository;
        this.iRegistrationRepository = iRegistrationRepository;
    }

    @Override
    public String createCourse(CourseDto courseDto) {
        return iCourseRepository.save(courseDto);
    }

    @Override
    public List<AllotResponse> allotCourse(String courseId) {
        CourseDto courseDto = iCourseRepository.findById(courseId).orElseThrow(()->new InvalidInputException("Course not found with id "+ courseId));
        if (courseDto.getMinStrength() > courseDto.getEmployeeList().size() && !courseDto.getStatus().equals("CANCELLED") ){
            courseDto.setStatus("ALLOTED");
            iCourseRepository.updateCourse(courseDto);
            List<AllotResponse> allotResponseList = iRegistrationRepository.findAllByCourseId(courseId).stream().map(r->getAllotResponse(courseDto, r)).collect(Collectors.toList());
            return allotResponseList;
        } else {
            throw new InvalidInputException("Given course with " +courseId+ "doen't exist");
        }
    }

    public AllotResponse getAllotResponse(CourseDto courseDto, RegistrationDto registration) {
        AllotResponse allotResponse = new AllotResponse(registration.getRegId(), registration.getEmailId(), courseDto.getCourseId(), courseDto.getCourseName(), courseDto.getInstructor(), courseDto.getDate(), courseDto.getStatus());
        return allotResponse;
    }

    @Override
    public CourseDto getCourse(String courseId) {
        return iCourseRepository.findById(courseId).orElseThrow(()->new InvalidInputException("Course with id " +courseId+ " doen't exist"));
    }
    
}
