package com.geektrust.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class EmployeeDto {
    @NonNull
    private final String name;
    @NonNull
    private final String email;
}
