package com.geektrust.backend.appConfig;

import com.geektrust.backend.Commands.AddCourseCommand;
import com.geektrust.backend.Commands.AllotCommand;
import com.geektrust.backend.Commands.CancelCommand;
import com.geektrust.backend.Commands.CommandInvoker;
import com.geektrust.backend.Commands.RegisterCommand;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IEmployeeRepository;
import com.geektrust.backend.repository.IRegistrationRepository;
import com.geektrust.backend.repository.repoImpl.CourseRepositoryImpl;
import com.geektrust.backend.repository.repoImpl.EmployeeRepositoryImpl;
import com.geektrust.backend.repository.repoImpl.RegistrationRepositoryImpl;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.service.IEmployeeService;
import com.geektrust.backend.service.IRegistrationService;
import com.geektrust.backend.service.serviceImpl.CourseServiceImpl;
import com.geektrust.backend.service.serviceImpl.EmployeeServiceImpl;
import com.geektrust.backend.service.serviceImpl.RegistrationServiceImpl;

public class ApplicationConfig {

    private final ICourseRepository iCourseRepository = new CourseRepositoryImpl();
    private final IEmployeeRepository iEmployeeRepository = new EmployeeRepositoryImpl();
    private final IRegistrationRepository iRegistrationRepository = new RegistrationRepositoryImpl();

    private final IEmployeeService iEmployeeService = new EmployeeServiceImpl(iEmployeeRepository);
    private final ICourseService iCourseService = new CourseServiceImpl(iCourseRepository, iRegistrationRepository, iEmployeeService);
    private final IRegistrationService iRegistrationService = new RegistrationServiceImpl(iRegistrationRepository, iEmployeeService, iCourseService);

    private final AddCourseCommand addCourseCommand = new AddCourseCommand(iCourseService);
    private final RegisterCommand registerCommand=new RegisterCommand(iRegistrationService);
    private final CancelCommand cancelCommand=new CancelCommand(iRegistrationService);
    private final AllotCommand allotCommand=new AllotCommand(iCourseService);
    
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("ADD-COURSE-OFFERING", addCourseCommand);
        commandInvoker.register("REGISTER", registerCommand);
        commandInvoker.register("ALLOT", allotCommand);
        commandInvoker.register("CANCEL", cancelCommand);
        return commandInvoker;
    }

}
