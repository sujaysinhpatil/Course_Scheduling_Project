package com.geektrust.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    // Regular expression pattern for validating email
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
