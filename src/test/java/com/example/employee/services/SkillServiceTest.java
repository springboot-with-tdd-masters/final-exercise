package com.example.employee.services;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.domain.entities.SkillEntity;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.repositories.EmployeeRepository;
import com.example.employee.repositories.SkillRepository;
import com.example.employee.services.adapters.SkillAdapter;
import com.example.employee.services.impl.SkillServiceImpl;
import com.example.employee.util.DateUtils;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillAdapter skillAdapter;

    private SkillService skillService;

    @BeforeEach
    void setUp() {
        skillService = new SkillServiceImpl(employeeRepository, skillRepository, skillAdapter);
    }

    @Test
    @DisplayName("findAllSkillsOf_shouldReturnPagedSkills")
    void findAllSkillsOfl_shouldReturnPagedSkills() {
        // Arrange
        Long employeeId = 1L;
        Pageable pageRequest = PageRequest.of(0, 2);
        List<SkillEntity> skillEntities = Arrays.asList(new SkillEntity(), new SkillEntity());
        Page<SkillEntity> skillEntityPage = new PageImpl<>(skillEntities, pageRequest, skillEntities.size());

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));
        when(skillRepository.findAllByEmployeeEntityId(employeeId, pageRequest))
                .thenReturn(skillEntityPage);

        // Act
        final Page<Skill> actual = skillService.findAllSkillsOf(employeeId, pageRequest);

        // Assert
        verify(employeeRepository)
                .findById(employeeId);
        verify(skillRepository)
                .findAllByEmployeeEntityId(employeeId, pageRequest);
        verify(skillAdapter, times(2))
                .convert(any(SkillEntity.class));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .isNotEmpty();
    }

    @Test
    @DisplayName("findAllSkillsOf_shouldThrowAnErrorWhenEmployeeNotFound")
    void findAllSkillsOf_shouldThrowAnErrorWhenEmployeeNotFound() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> skillService.findAllSkillsOf(1L, Pageable.unpaged()));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("findSkillOf_shouldReturnSkillWithGivenIdAndEmployeeId")
    void findSkillOf_shouldReturnSkillWithGivenIdAndEmployeeId() {
        // Arrange
        Long employeeId = 1L;
        Long skillId = 1L;

        final SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(skillId);

        final Skill skill = new Skill();
        skill.setId(employeeId);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));
        when(skillRepository.findByEmployeeEntityIdAndId(employeeId, skillId))
                .thenReturn(Optional.ofNullable(skillEntity));
        when(skillAdapter.convert(skillEntity))
                .thenReturn(skill);

        // Act
        final Skill actual = skillService.findSkillOf(employeeId, skillId);

        // Assert
        verify(employeeRepository)
                .findById(employeeId);
        verify(skillRepository)
                .findByEmployeeEntityIdAndId(employeeId, skillId);
        verify(skillAdapter)
                .convert(any(SkillEntity.class));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("id")
                .isEqualTo(skillId);
    }

    @Test
    @DisplayName("findSkillOf_shouldThrowAnErrorWhenEmployeeNotFound")
    void findSkillOf_shouldThrowAnErrorWhenEmployeeNotFound() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> skillService.findSkillOf(1L, 1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("findSkillOf_shouldThrowAnErrorWhenSkillNotFound")
    void findSkillOf_shouldThrowAnErrorWhenSkillNotFound() {
        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));

        // Act
        final Throwable throwable = catchThrowable(() -> skillService.findSkillOf(1L, 1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Skill not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("createSkillOf_shouldAcceptEmployeeRequestAndSaveToRepositoryAndReturnProperValues")
    void createSkillOf_shouldAcceptEmployeeRequestAndSaveToRepositoryAndReturnProperValues() {
        // Arrange
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";
        final LocalDateTime lastUsed = LocalDateTime.of(2022, Month.JUNE, 3, 0, 0 ,0);

        final Long employeeId = 1L;
        final SkillRequest createRequest = SkillRequest.of(description, duration, lastUsedAsString);
        final SkillEntity skillEntity = new SkillEntity(description, duration, lastUsed);
        final Skill skill = new Skill(description, duration, lastUsedAsString);

        when(skillAdapter.convert(createRequest))
                .thenReturn(skillEntity);
        when(skillAdapter.convert(skillEntity))
                .thenReturn(skill);
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));

        // Act
        final Skill actual = skillService.createSkillOf(employeeId, createRequest);

        // Assert
        verify(skillAdapter)
                .convert(ArgumentMatchers.<SkillRequest>argThat(paramRequest -> Objects.equals(paramRequest, createRequest)));
        verify(skillRepository)
                .save(skillEntity);
        verify(skillAdapter)
                .convert(ArgumentMatchers.<SkillEntity>argThat(paramRequest -> Objects.equals(paramRequest, skillEntity)));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("description", "duration", "lastUsed")
                .containsExactly(description, duration, lastUsedAsString);
    }

    @Test
    @DisplayName("createSkillOf_shouldThrowAnErrorWhenEmployeeNotFound")
    void createSkillOf_shouldThrowAnErrorWhenEmployeeNotFound() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> skillService.createSkillOf(1L, SkillRequest.empty()));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("updateSkillOf_shouldAcceptEmployeeRequestAndUpdateExistingEmployee")
    void updateSkillOf_shouldAcceptEmployeeRequestAndUpdateExistingEmployee() {
        // Arrange
        final String changeRequestDescription = "jQuery";
        final Integer changeRequestDuration = 7;
        final String changeLastUsedRequestDateAsString = "2021-01-03";
        final LocalDateTime lastUsed = LocalDateTime.of(2022, Month.JUNE, 3, 0, 0 ,0);

        final Long employeeId = 1L;
        final Long existingSkillId = 1L;
        final SkillRequest changeRequest = SkillRequest.of(changeRequestDescription, changeRequestDuration, changeLastUsedRequestDateAsString);
        final SkillEntity existingSkillEntity = new SkillEntity("Java 8", 7, lastUsed);
        final Skill skill = new Skill(changeRequestDescription, changeRequestDuration, changeLastUsedRequestDateAsString);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));
        when(skillRepository.findByEmployeeEntityIdAndId(employeeId, existingSkillId))
                .thenReturn(Optional.ofNullable(existingSkillEntity));
        when(skillAdapter.convert(existingSkillEntity))
                .thenReturn(skill);

        // Act
        final Skill actual = skillService.updateSkillOf(employeeId, existingSkillId, changeRequest);

        // Assert
        verify(employeeRepository)
                .findById(employeeId);
        verify(skillRepository)
                .findByEmployeeEntityIdAndId(employeeId, existingSkillId);
        verify(skillAdapter)
                .convert(existingSkillEntity);
        verify(skillRepository)
                .save(existingSkillEntity);

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("description", "duration", "lastUsed")
                .containsExactly(changeRequestDescription, changeRequestDuration, changeLastUsedRequestDateAsString);
    }

    @Test
    @DisplayName("updateSkillOf_shouldThrowAnErrorWhenEmployeeDoesntExists")
    void updateSkillOf_shouldThrowAnErrorWhenEmployeeDoesntExists() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> skillService.updateSkillOf(1L, 1L, SkillRequest.empty()));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("updateSkillOf_shouldThrowAnErrorWhenSkillNotFound")
    void updateSkillOf_shouldThrowAnErrorWhenSkillNotFound() {
        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));

        // Act
        final Throwable throwable = catchThrowable(() -> skillService.updateSkillOf(1L, 1L, SkillRequest.empty()));

        // Assert
        assertThat(throwable)
                .hasMessage("Skill not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("deleteSkillOf_shouldDeleteEmployeeByGivenId")
    void deleteSkillOf_shouldDeleteEmployeeByGivenId() {
        // Arrange
        final Long employeeId = 1L;
        final Long skillIdToDelete = 1L;

        final SkillEntity existingSkillEntity = new SkillEntity();
        existingSkillEntity.setId(skillIdToDelete);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));
        when(skillRepository.findByEmployeeEntityIdAndId(employeeId, skillIdToDelete))
                .thenReturn(Optional.ofNullable(existingSkillEntity));

        // Act
        skillService.deleteSkillOf(employeeId, skillIdToDelete);

        // Assert
        verify(employeeRepository)
                .findById(employeeId);
        verify(skillRepository)
                .findByEmployeeEntityIdAndId(employeeId, skillIdToDelete);
        verify(skillRepository)
                .delete(existingSkillEntity);
    }

    @Test
    @DisplayName("deleteSkillOf_shouldThrowAnErrorWhenSkillNotFound")
    void deleteSkillOf_shouldThrowAnErrorWhenSkillNotFound() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> skillService.deleteSkillOf(1L, 1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("deleteSkillOf_shouldThrowAnErrorWhenEmployeeDoesntExists")
    void deleteSkillOf_shouldThrowAnErrorWhenEmployeeDoesntExists() {
        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(new EmployeeEntity()));

        // Act
        final Throwable throwable = catchThrowable(() -> skillService.deleteSkillOf(1L, 1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Skill not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }
}