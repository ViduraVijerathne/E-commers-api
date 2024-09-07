/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import exceptions.ValidationException;

/**
 *
 * @author vidur
 */
public class Validators {

    public static boolean validateField(String value, String fieldName, int minLength, int maxLength) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null.");
        }
        if (value.length() < minLength || value.length() > maxLength) {
            throw new ValidationException(fieldName + " must be between " + minLength + " and " + maxLength + " characters long.");
        }
        return true;
    }

    public static boolean validateFirstName(String firstName) throws ValidationException {
        return validateField(firstName, "First Name", 2, 45);
    }

    public static boolean validateLastName(String lastName) throws ValidationException {
        return validateField(lastName, "Last Name", 2, 45);
    }

    public static boolean validateEmail(String email) throws ValidationException {
        // Simple regex for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        validateField(email, "Email", 5, 45); // Assuming a minimum length of 5 for email
        if (!email.matches(emailRegex)) {
            throw new ValidationException("Invalid email format.");
        }
        return true;
    }

    public static boolean validatePassword(String password) throws ValidationException {
        // You can add additional password rules here (e.g., at least one number, one special character, etc.)
        return validateField(password, "Password", 8, 45); // Assuming a minimum length of 8 for password
    }

    public static boolean validateColor(String color) {
        // Regular expression for a valid hex color code (3 or 6 hexadecimal characters)
        String hexPattern = "^[A-Fa-f0-9]{6}$|^[A-Fa-f0-9]{3}$";

        // Validate if the color matches the hex pattern
        return color != null && color.matches(hexPattern);
    }
}
