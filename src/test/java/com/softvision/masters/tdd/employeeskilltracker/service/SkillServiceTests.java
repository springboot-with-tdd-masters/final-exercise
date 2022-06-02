package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.repository.SkillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTests {

    @Mock
    SkillRepository mockBookRepository;
    @InjectMocks
    SkillService bookService = new SkillServiceImpl();

    @Mock
    Pageable mockPageable;
    @Mock
    Page<Skill> mockBookPage;
    @Mock
    Skill mockSkill;
    @Mock
    Skill mockSkillExpected;

    @Test
    @DisplayName("Get All - should get a page of books by the employee ID")
    void test_getAllByEmployee() {
        when(mockBookPage.hasContent()).thenReturn(true);
        when(mockBookRepository.findByEmployeeId(1L, mockPageable)).thenReturn(mockBookPage);
        assertSame(mockBookPage, bookService.getAllByEmployee(1L, mockPageable));
        verify(mockBookRepository, atMostOnce()).findByEmployeeId(anyLong(), any());
    }

    @Test
    @DisplayName("Get All - should throw RecordNotFoundException when ")
    void test_getAllByEmployee_throwRecordNotFoundException() {
        when(mockBookPage.hasContent()).thenReturn(false);
        when(mockBookRepository.findByEmployeeId(1L, mockPageable)).thenReturn(mockBookPage);
        assertThrows(RecordNotFoundException.class, () -> bookService.getAllByEmployee(1L, mockPageable));
        verify(mockBookRepository, atMostOnce()).findByEmployeeId(anyLong(), any());
    }

    @Test
    @DisplayName("Create - should create a Book")
    void test_create() {
        when(mockBookRepository.save(mockSkill)).thenReturn(mockSkillExpected);
        assertSame(mockSkillExpected, bookService.create(mockSkill));
        verify(mockBookRepository, atMostOnce()).save(any());
    }
}
