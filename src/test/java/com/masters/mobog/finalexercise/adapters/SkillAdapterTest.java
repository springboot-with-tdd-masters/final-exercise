package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillAdapterTest {

    private SkillAdapter skillAdapter;
    @BeforeEach
    void setup(){
        skillAdapter = new SkillAdapter();
    }
    @Test
    @DisplayName("should map successfully")
    void shouldMapSuccessfully(){
        EmployeeSkillRequest request = new EmployeeSkillRequest();
        request.setDescription("Descript");
        request.setDuration(5);
        request.setLastUsed(LocalDate.now());
        Skill actual = skillAdapter.mapToSkill(request);
        assertEquals("Descript", actual.getDescription());
        assertEquals(5, actual.getDuration());
        assertEquals(LocalDate.now(), actual.getLastUsed());
    }
}
