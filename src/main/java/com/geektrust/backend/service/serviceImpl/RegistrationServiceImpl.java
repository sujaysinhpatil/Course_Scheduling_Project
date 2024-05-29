package com.geektrust.backend.service.serviceImpl;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.dto.CourseDto;
import com.geektrust.backend.dto.EmployeeDto;
import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.service.IEmployeeService;
import com.geektrust.backend.service.IRegistrationService;

public class RegistrationServiceImpl implements IRegistrationService {

    private IRegistrationRepository iRegistrationRepository;
    private IEmployeeService iEmployeeService;
    private ICourseService iCourseService;

    public RegistrationServiceImpl(IRegistrationRepository iRegistrationRepository, IEmployeeService iEmployeeService,
            ICourseService iCourseService) {
        this.iRegistrationRepository = iRegistrationRepository;
        this.iEmployeeService = iEmployeeService;
        this.iCourseService = iCourseService;
    }

    @Override
    public String create(RegistrationDto registrationDto) {
        
        String coourseRegId = getCourseRegId(registrationDto);
        registrationDto.setAccepted(true);
        registrationDto.setRegId(coourseRegId);
        return iRegistrationRepository.save(registrationDto);
    }

    private String getCourseRegId(RegistrationDto registrationDto) {
        if(!iEmployeeService.existsById(registrationDto.getEmailId())) {
            iEmployeeService.save(new EmployeeDto(registrationDto.getEmailId()));
        }
        EmployeeDto empDto = iEmployeeService.getEmployee(registrationDto.getEmailId());
        CourseDto courseDto = iCourseService.getCourse(registrationDto.getCourseId());
        int regEmpSize = iRegistrationRepository.findAllByCourseId(courseDto.getCourseId()).size();
        if(regEmpSize >= courseDto.getMaxStrength()) {
            throw new InvalidInputException("COURSE_FULL_ERROR");
        }
        if("CONFIRMED".equals(courseDto.getStatus())) {
            throw new InvalidInputException("COURSE_ALREADY_ALLOTED");
        }
        return "REG-COURSE-"+empDto.getName()+"-"+courseDto.getCourseName();
    }

    @Override
    public String cancelRegistration(String regId) {
        RegistrationDto registrationDto = iRegistrationRepository.findById(regId).get();
        CourseDto courseDto = iCourseService.getCourse(registrationDto.getCourseId());
        if ("CONFIRMED".equals(courseDto.getStatus())) {
            return regId+" "+"CANCEL_REJECTED";
        } else {
            iRegistrationRepository.deleteById(regId);
            return regId+" "+"CANCEL_ACCEPTED";
        }
    }
    
}
