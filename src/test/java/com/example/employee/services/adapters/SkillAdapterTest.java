package com.example.employee.services.adapters;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.domain.entities.SkillEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SkillAdapterTest {

    private SkillAdapter skillAdapter;

    @BeforeEach
    void setUp() {
        skillAdapter = new SkillAdapter();
    }

    @Test
    @DisplayName("convert_shouldAcceptSkillRequestAndConvertToSkillEntity")
    void convert_shouldAcceptSkillRequestAndConvertToSkillEntity() {
        // Arrange
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";

        final SkillRequest skillRequest = SkillRequest.of(description, duration, lastUsedAsString);

        // Act
        final SkillEntity actual = skillAdapter.convert(skillRequest);

        // Assert
        assertNotNull(actual);
        assertEquals(description, actual.getDescription());
        assertEquals(duration, actual.getDuration());

        final LocalDateTime lastUsed = actual.getLastUsed();
        assertEquals(2022, lastUsed.getYear());
        assertEquals(Month.JUNE, lastUsed.getMonth());
        assertEquals(3, lastUsed.getDayOfMonth());

        assertNull(actual.getId());
        assertNull(actual.getCreatedDate());
        assertNull(actual.getLastModifiedDate());
    }

    @Test
    @DisplayName("convert_shouldAcceptSkillEntityAndConvertToSkill")
    void convert_shouldAcceptSkillEntityAndConvertToSkill() {
        // Arrange

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final Long id = 1L;
        final Long employeeId = 1L;
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";
        final LocalDateTime now = LocalDateTime.now();

        final EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);

        final SkillEntity skillEntity = new SkillEntity();

        skillEntity.setId(id);
        skillEntity.setDescription(description);
        skillEntity.setDuration(duration);
        skillEntity.setLastUsed(LocalDate.parse(lastUsedAsString, formatter)
                .atStartOfDay());
        skillEntity.setEmployeeEntity(employeeEntity);
        skillEntity.setCreatedDate(now);
        skillEntity.setLastModifiedDate(now);

        // Act
        final Skill actual = skillAdapter.convert(skillEntity);

        // Assert
        assertNotNull(actual);
        assertEquals(id, actual.getId());
        assertEquals(description, actual.getDescription());
        assertEquals(duration, actual.getDuration());
        assertEquals(employeeId, actual.getEmployeeId());
        assertEquals(lastUsedAsString, actual.getLastUsed());
        assertEquals(now.format(formatter), actual.getCreatedDate());
        assertEquals(now.format(formatter), actual.getLastModifiedDate());
    }
}