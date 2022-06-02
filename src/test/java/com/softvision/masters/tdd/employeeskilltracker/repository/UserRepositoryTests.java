package com.softvision.masters.tdd.employeeskilltracker.repository;

import com.softvision.masters.tdd.employeeskilltracker.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.softvision.masters.tdd.employeeskilltracker.mocks.UserMocks.*;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("should get the exact saved user given its username")
    void test_findByUsername() {
        User expected = userRepository.save(getMockUser1());

        User actual = userRepository.findByUsername(MOCK_USER1_USERNAME).orElseGet(Assertions::fail);

        Assertions.assertEquals(expected.getUsername(), actual.getUsername());
        Assertions.assertEquals(expected.getRole(), actual.getRole());
        Assertions.assertEquals(expected.getId(), actual.getId());
    }
}
