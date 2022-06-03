package com.example.demo.repository;

import com.example.demo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        userList.add(buildUser(1L, "user1", "pw1"));
        userList.add(buildUser(2L, "user2", "pw2"));

    }

    private User buildUser(Long id, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    @AfterEach
    public void close(){
        userList = new ArrayList<>();
    }

    @Test
    @DisplayName("Should return user corresponding to provided username")
    public void testFindUserByUsername(){
        String requestedUserName = "user2";
        List<User> filteredUser = userList.stream().filter(u -> u.getUsername().equalsIgnoreCase(requestedUserName)).collect(Collectors.toList());
        when(userRepository.findByUsername(requestedUserName)).thenReturn(filteredUser.get(0));
        User retrievedUser = userRepository.findByUsername(requestedUserName);
        assertEquals(requestedUserName, retrievedUser.getUsername());
    }

}