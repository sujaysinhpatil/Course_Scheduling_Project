package com.geektrust.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationDto {
    private String regId;
    private final  String emailId;
    private final  String courseId;
    private boolean isAccepted;
}
