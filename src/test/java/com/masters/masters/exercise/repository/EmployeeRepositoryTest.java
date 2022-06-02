package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.Employees;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeesRepository repository;

    @Test
    public void saveHappyPath(){
        Employees entity = new Employees();
        entity.setLastName("lastName");
        entity.setFirstName("firstName");
        Employees savedEntity = repository.save(entity);
        Assertions.assertEquals("lastName",savedEntity.getLastName());
        Assertions.assertEquals("firstName",savedEntity.getFirstName());
        Assertions.assertNotNull(savedEntity.getId());
    }
}
