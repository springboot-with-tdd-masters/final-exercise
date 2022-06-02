package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import com.example.demo.dto.SkillRequest;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SkillServiceImplTest {

    @MockBean
    private SkillServiceImpl skillServiceImpl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    private List<Employee> employeeList = new ArrayList<>();
    private SkillRequest skillRequest = new SkillRequest();
    @BeforeEach
    public void setUp(){

        skillRequest.setDescription("Java");
        skillRequest.setDuration(10);
        skillRequest.setLastUsed("2020-11-11");

        skillRepository.save(Skill.builder().lastUsed(LocalDate.parse("2020-11-11")).description("Java").duration(10).employee(addEmployee("Jay", "Sean", skillRequest)).build());

    }

    private Employee addEmployee(String firstName, String lastName, SkillRequest skillRequest) {
        return employeeRepository.save(Employee.builder().firstName(firstName).lastName(lastName).build());
    }

    private Set<Skill> addSkill(SkillRequest skillRequest) {
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(Skill.builder().description(skillRequest.getDescription()).duration(skillRequest.getDuration()).lastUsed(LocalDate.parse(skillRequest.getLastUsed())).build());
        return skillSet;
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successCreateBook() throws Exception {
        skillServiceImpl.updateSkill(1L, 1L, skillRequest);
    }

}