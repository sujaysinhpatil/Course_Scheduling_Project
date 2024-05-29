package com.geektrust.backend.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.AllotResponse;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.service.IEmployeeService;

public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository iCourseRepository;
    private final IRegistrationRepository iRegistrationRepository;
    private final IEmployeeService iEmployeeService;

    public CourseServiceImpl(ICourseRepository iCourseRepository, IRegistrationRepository iRegistrationRepository, IEmployeeService iEmployeeService) {
        this.iCourseRepository = iCourseRepository;
        this.iRegistrationRepository = iRegistrationRepository;
        this.iEmployeeService = iEmployeeService;
    }

    @Override
    public String createCourse(CourseDto courseDto) {
        return iCourseRepository.save(courseDto);
    }

    @Override
    public List<AllotResponse> allotCourse(String courseId) {
        CourseDto courseDto = iCourseRepository.findById(courseId).orElseThrow(()->new InvalidInputException("Course not found with id "+ courseId));
        List<EmployeeDto> employeeList = getEmployeeList(courseId);
        if (courseDto.getMinStrength() <= employeeList.size() && !"CANCELLED".equals(courseDto.getStatus())) {
            courseDto.setStatus("CONFIRMED");
            courseDto.setEmployeeList(employeeList);
            iCourseRepository.updateCourse(courseDto);
            List<AllotResponse> allotResponseList = iRegistrationRepository.findAllByCourseId(courseId).stream().map(r->getAllotResponse(courseDto, r)).collect(Collectors.toList());
            return allotResponseList;
        } else {
            throw new InvalidInputException("Given course with " +courseId+ " is CANCELLED or not enough registration");
        }
    }

    private List<EmployeeDto> getEmployeeList(String courseId) {
        return iRegistrationRepository.findAllByCourseId(courseId).stream().map(r->iEmployeeService.getEmployee(r.getEmailId())).collect(Collectors.toList());
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
