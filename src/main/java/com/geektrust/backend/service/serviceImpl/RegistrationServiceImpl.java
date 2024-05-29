package com.geektrust.backend.service.serviceImpl;

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
        System.out.println(coourseRegId);
        registrationDto.setAccepted(true);
        registrationDto.setRegId(coourseRegId);
        return iRegistrationRepository.save(registrationDto);
    }

    // course-registration-id is REG-COURSE-<EMPLOYEE-NAME>-<COURSE-NAME>
    private String getCourseRegId(RegistrationDto registrationDto) {
        EmployeeDto empDto = iEmployeeService.getEmployee(registrationDto.getEmailId());
        CourseDto courseDto = iCourseService.getCourse(registrationDto.getCourseId());
        return "REG-COURSE-"+empDto.getName()+"-"+courseDto.getCourseName();
    }

    @Override
    public String cancelRegistration(String regId) {
        // CANCEL_REJECTED
        RegistrationDto registrationDto = iRegistrationRepository.findById(regId).get();
        CourseDto courseDto = iCourseService.getCourse(registrationDto.getCourseId());
        if(courseDto.getStatus().equals("ALLOTED")) {
            return "CANCEL_REJECTED";
        } else {
            registrationDto.setAccepted(false);
            iRegistrationRepository.save(registrationDto);
            return regId;
        }
    }
    
}
