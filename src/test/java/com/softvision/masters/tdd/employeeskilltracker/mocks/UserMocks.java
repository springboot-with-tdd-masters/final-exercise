package com.softvision.masters.tdd.employeeskilltracker.mocks;

import com.softvision.masters.tdd.employeeskilltracker.model.User;

public class UserMocks {

    public static final String MOCK_USER1_USERNAME = "mhatch";
    public static final String MOCK_USER1_PASSWORD = "hello123";
    public static final String MOCK_USER1_ROLE = "admin";
    public static final String MOCK_USER2_USERNAME = "sdiamon";
    public static final String MOCK_USER2_PASSWORD = "welcome123";
    public static final String MOCK_USER2_ROLE = "user";

    public static User getMockUser1() {
        return new User(MOCK_USER1_USERNAME, MOCK_USER1_PASSWORD, MOCK_USER1_ROLE);
    }

    public static User getMockUser2() {
        return new User(MOCK_USER2_USERNAME, MOCK_USER2_PASSWORD, MOCK_USER2_ROLE);
    }
}
