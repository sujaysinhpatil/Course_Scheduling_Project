package com.geektrust.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllotResponse {
    private String regId;
    private String emailId;
    private String courseId;
    private String courseName;
    private String instructor;
    private String date;
    private String status;

    // <course-registration-id> <email-id> <course-offering-id> <course-name> <instructor> <date-in-ddmmyyyy> <status>
    @Override
    public String toString() {
        return regId +" "+ emailId +" "+ courseId +" "+ courseName +" "+ instructor +" "+ date +" "+ status;
    }
}
