package com.geektrust.backend.repository;

import java.util.List;

import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.entity.Registration;

public interface IRegistrationRepository extends CRUDRepository<RegistrationDto,String> {
    List<RegistrationDto> findAllByCourseId(String courseId);
    Registration getRegistration(RegistrationDto registrationDto);
    RegistrationDto getRegistrationDto(Registration registration);
}
