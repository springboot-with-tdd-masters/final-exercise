package com.example.employee.controllers.advices;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public void addViolation(final String field, final String message) {
        violations.add(new Violation(field, message));
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public class Violation {

        final String fieldName;
        final String message;

        public Violation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getMessage() {
            return message;
        }
    }
}
