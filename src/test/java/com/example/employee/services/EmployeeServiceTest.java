package com.example.employee.services;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.repositories.EmployeeRepository;
import com.example.employee.services.adapters.EmployeeAdapter;
import com.example.employee.services.impl.EmployeeServiceImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeAdapter employeeAdapter;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeAdapter);
    }

    @Test
    @DisplayName("findAll_shouldReturnPagedEmployees")
    void findAll_shouldReturnPagedEmployees() {
        // Arrange
        Pageable pageRequest = PageRequest.of(0, 2);
        List<EmployeeEntity> employeeEntities = Arrays.asList(new EmployeeEntity(), new EmployeeEntity());
        Page<EmployeeEntity> employeeEntityPage = new PageImpl<>(employeeEntities, pageRequest, employeeEntities.size());

        when(employeeRepository.findAll(pageRequest))
                .thenReturn(employeeEntityPage);

        // Act
        final Page<Employee> actual = employeeService.findAll(pageRequest);

        // Assert
        verify(employeeRepository)
                .findAll(pageRequest);
        verify(employeeAdapter, times(2))
                .convert(any(EmployeeEntity.class));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .isNotEmpty();
    }

    @Test
    @DisplayName("findById_shouldReturnEmployeeWithGivenId")
    void findById_shouldReturnEmployeeWithGivenId() {
        // Arrange
        Long employeeId = 1L;

        final EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);

        final Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.ofNullable(employeeEntity));
        when(employeeAdapter.convert(employeeEntity))
                .thenReturn(employee);

        // Act
        final Employee actual = employeeService.findById(employeeId);

        // Assert
        verify(employeeRepository)
                .findById(employeeId);
        verify(employeeAdapter)
                .convert(any(EmployeeEntity.class));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("id")
                .isEqualTo(employeeId);
    }

    @Test
    @DisplayName("findById_shouldThrowAnErrorWhenEmployeeNotFound")
    void findById_shouldThrowAnErrorWhenEmployeeNotFound() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> employeeService.findById(1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("create_shouldAcceptEmployeeRequestAndSaveToRepositoryAndReturnProperValues")
    void create_shouldAcceptEmployeeRequestAndSaveToRepositoryAndReturnProperValues() {
        // Arrange
        final String firstName = "James";
        final String lastName = "Rod";

        final EmployeeRequest createRequest = EmployeeRequest.of(firstName, lastName);
        final EmployeeEntity employeeEntity = new EmployeeEntity(firstName, lastName);
        final Employee employee = new Employee(firstName, lastName);

        when(employeeAdapter.convert(createRequest))
                .thenReturn(employeeEntity);
        when(employeeAdapter.convert(employeeEntity))
                .thenReturn(employee);

        // Act
        final Employee actual = employeeService.create(createRequest);

        // Assert
        verify(employeeAdapter)
                .convert(ArgumentMatchers.<EmployeeRequest>argThat(paramRequest -> Objects.equals(paramRequest, createRequest)));
        verify(employeeAdapter)
                .convert(ArgumentMatchers.<EmployeeEntity>argThat(paramRequest -> Objects.equals(paramRequest, employeeEntity)));

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("firstName", "lastName")
                .containsExactly(firstName, lastName);
    }

    @Test
    @DisplayName("update_shouldAcceptEmployeeRequestAndUpdateExistingEmployee")
    void update_shouldAcceptEmployeeRequestAndUpdateExistingEmployee() {
        // Arrange
        final Long existingEmployeeId = 1L;
        final String firstName = "Updated First Name";
        final String lastName = "Updated Last Name";

        final EmployeeRequest updateRequest = EmployeeRequest.of(firstName, lastName);
        final EmployeeEntity existingEmployeeEntity = new EmployeeEntity("Existing First Name", "Existing Last Name");
        final Employee employee = new Employee(firstName, lastName);

        when(employeeRepository.findById(existingEmployeeId))
                .thenReturn(Optional.ofNullable(existingEmployeeEntity));
        when(employeeAdapter.convert(existingEmployeeEntity))
                .thenReturn(employee);

        // Act
        final Employee actual = employeeService.update(existingEmployeeId, updateRequest);

        // Assert
        verify(employeeRepository)
                .findById(existingEmployeeId);
        verify(employeeAdapter)
                .convert(existingEmployeeEntity);
        verify(employeeRepository)
                .save(existingEmployeeEntity);

        assertThat(actual)
                .isNotNull();
        assertThat(actual)
                .extracting("firstName", "lastName")
                .containsExactly(firstName, lastName);
    }

    @Test
    @DisplayName("update_shouldThrowAnErrorWhenEmployeeDoesntExists")
    void update_shouldThrowAnErrorWhenEmployeeDoesntExists() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> employeeService.update(1L, EmployeeRequest.of("", "")));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }

    @Test
    @DisplayName("delete_shouldDeleteEmployeeByGivenId")
    void delete_shouldDeleteEmployeeByGivenId() {
        // Arrange
        final Long employeeIdToDelete = 1L;

        final EmployeeEntity existingEmployeeEntity = new EmployeeEntity();
        existingEmployeeEntity.setId(employeeIdToDelete);

        when(employeeRepository.findById(employeeIdToDelete))
                .thenReturn(Optional.ofNullable(existingEmployeeEntity));

        // Act
        employeeService.delete(employeeIdToDelete);

        // Assert
        verify(employeeRepository)
                .findById(employeeIdToDelete);
        verify(employeeRepository)
                .delete(existingEmployeeEntity);
    }

    @Test
    @DisplayName("delete_shouldThrowAnErrorWhenEmployeeDoesntExists")
    void delete_shouldThrowAnErrorWhenEmployeeDoesntExists() {
        // Arrange
        // Act
        final Throwable throwable = catchThrowable(() -> employeeService.delete(1L));

        // Assert
        assertThat(throwable)
                .hasMessage("Employee not found.")
                .asInstanceOf(InstanceOfAssertFactories.type(DefaultException.class));
    }
}