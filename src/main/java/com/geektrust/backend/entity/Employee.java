package com.geektrust.backend.entity;

import com.geektrust.backend.Exception.InvalidInputException;
import com.geektrust.backend.utils.EmailValidator;

import lombok.Data;

@Data
public class Employee {
    private final String name;
    private final String email;
    public Employee(String email) {
        if(EmailValidator.isValidEmail(email)) {
            this.name = email.substring(0, email.indexOf("@"));
        } else {
            throw new InvalidInputException("INPUT_DATA_ERROR");
        }
        this.email = email;
    }
}
