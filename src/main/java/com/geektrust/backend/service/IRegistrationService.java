package com.geektrust.backend.service;

import com.geektrust.backend.dto.RegistrationDto;

public interface IRegistrationService {
    String create(RegistrationDto registrationDto);
    String cancelRegistration(String regId);
}
