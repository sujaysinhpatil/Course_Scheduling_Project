package com.geektrust.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class Registration {
    private String regId;
    private final  String emailId;
    private final  String courseId;
    private boolean isAccepted;
}
