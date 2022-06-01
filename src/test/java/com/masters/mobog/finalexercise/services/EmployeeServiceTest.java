package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.adapters.EmployeeAdapter;
import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private EmployeeService service;

    @Mock
    private EmployeeRepository repository;

    private EmployeeAdapter adapter;

    @BeforeEach
    void setup(){
        adapter = new EmployeeAdapter();
        service = new EmployeeServiceImpl(repository, adapter);
    }

    @Test
    @DisplayName("should create employee")
    void shouldCreateEmployee() {
        // given
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstname("Jonny");
        request.setLastname("Braveaux");

        Employee stub = new Employee();
        stub.setId(1L);
        stub.setFirstname("Jay");
        stub.setLastname("Rock");
        stub.setCreatedDate(LocalDateTime.now());
        stub.setLastModifiedDate(LocalDateTime.now());
        when(repository.save(any(Employee.class))).thenReturn(stub);
        // when
        Employee actual = service.createEmployee(request);
        verify(repository).save(any(Employee.class));
        assertNotNull(actual.getId());
    }
    @Test
    @DisplayName("should find employee")
    void shouldFindEmployee() {
        Employee stub = new Employee();
        stub.setId(1L);
        stub.setFirstname("Jay");
        stub.setLastname("Rock");
        stub.setCreatedDate(LocalDateTime.now());
        stub.setLastModifiedDate(LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(stub));
        // when
        Employee actual = this.service.findById(1L);
        verify(repository).findById(1L);
        assertEquals("Jay", actual.getFirstname());
        assertEquals("Rock", actual.getLastname());

    }
    @Test
    @DisplayName("should find employee with page")
    void shouldFindEmployeesWithPage() {
        Employee emp1 = new Employee();
        emp1.setId(2L);
        emp1.setFirstname("Jay");
        emp1.setLastname("Rock");
        emp1.setCreatedDate(LocalDateTime.now());
        emp1.setLastModifiedDate(LocalDateTime.now());

        Employee emp2 = new Employee();
        emp2.setId(1L);
        emp2.setFirstname("Jay");
        emp2.setLastname("Rock");
        emp2.setCreatedDate(LocalDateTime.now());
        emp2.setLastModifiedDate(LocalDateTime.now());
        Page<Employee> page = mock(Page.class);
        when(page.getContent()).thenReturn(List.of(emp1, emp2));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        Page<Employee> employees = service.findAll(Pageable.unpaged());
        assertEquals(List.of(emp1, emp2), employees.getContent());
    }
    @Test
    @DisplayName("should update employee")
    void shouldUpdateEmployee() {
        // given
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstname("Jay");
        request.setFirstname("Rock");

        Employee stub = new Employee();
        stub.setId(1L);
        stub.setFirstname("Jay");
        stub.setLastname("Rock");
        stub.setCreatedDate(LocalDateTime.now());
        stub.setLastModifiedDate(LocalDateTime.now());
        when(repository.findById(anyLong())).thenReturn(Optional.of(stub));
        when(repository.save(any(Employee.class))).thenReturn(stub);
        // when
        Employee actual = service.update(1L, request);
        verify(repository).findById(anyLong());
        verify(repository).save(any(Employee.class));
        assertNotNull(actual.getId());


    }
    @Test
    @DisplayName("should delete employee")
    void shouldDeleteEmployee() {
        Employee stub = new Employee();
        stub.setId(1L);
        stub.setFirstname("Jay");
        stub.setLastname("Rock");
        stub.setCreatedDate(LocalDateTime.now());
        stub.setLastModifiedDate(LocalDateTime.now());
        when(repository.findById(anyLong())).thenReturn(Optional.of(stub));
        // when
        service.delete(stub);
        // then
        verify(repository).delete(stub);

    }
}
