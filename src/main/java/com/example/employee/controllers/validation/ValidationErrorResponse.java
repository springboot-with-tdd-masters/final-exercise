package com.example.employee.controllers.validation;

import java.util.Collection;
import java.util.TreeSet;

public class ValidationErrorResponse {

    private Collection<Violation> violations = new TreeSet<>();

    public void addViolation(final String field, final String message) {
        violations.add(new Violation(field, message));
    }

    public Collection<Violation> getViolations() {
        return violations;
    }

    public class Violation implements Comparable<Violation> {

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

        @Override
        public int compareTo(Violation o) {
            return this.fieldName.compareTo(o.fieldName);
        }
    }
}
