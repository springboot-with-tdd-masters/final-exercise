package com.finalexam.skills.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.finalexam.skills.exception.EmployeeNotFoundException;
import com.finalexam.skills.model.dto.mapper.EmployeeMapper;
import com.finalexam.skills.model.dto.request.EmployeeRequestDto;
import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.entity.Employee;
import com.finalexam.skills.repository.EmployeeRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private EmployeeMapper employeeMapper;

  @InjectMocks
  private EmployeeService employeeService = new EmployeeServiceImpl();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Save - save new employee")
  public void saveEmployeeTest() {

    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("Given", "Sur");

    Employee newEmployeeEntity = new Employee();
    newEmployeeEntity.setFirstname("Given");
    newEmployeeEntity.setLastname("Sur");

    Employee mockedEmployeeEntityFromRepo = mock(Employee.class);
    when(employeeRepository.save(newEmployeeEntity)).thenReturn(mockedEmployeeEntityFromRepo);

    EmployeeDto mockedSavedEmployee = mock(EmployeeDto.class);
    when(employeeMapper.convert(mockedEmployeeEntityFromRepo)).thenReturn(mockedSavedEmployee);

    EmployeeDto actualEmployee = employeeService.save(employeeRequestDto);

    assertThat(actualEmployee.getId()).isEqualTo(mockedSavedEmployee.getId());
    assertThat(actualEmployee.getFirstname()).isEqualTo(mockedSavedEmployee.getFirstname());
    assertThat(actualEmployee.getLastname()).isEqualTo(mockedSavedEmployee.getLastname());

    verify(employeeRepository).save(newEmployeeEntity);
    verify(employeeMapper).convert(mockedEmployeeEntityFromRepo);
  }

  @Test
  @DisplayName("Get employee details by id")
  public void getOneEmployeeById() {

    Employee expectedEmployee = new Employee();
    expectedEmployee.setId(1L);
    expectedEmployee.setFirstname("Given");
    expectedEmployee.setLastname("Sur");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));

    EmployeeDto mockedSavedEmployee = mock(EmployeeDto.class);
    when(employeeMapper.convert(expectedEmployee)).thenReturn(mockedSavedEmployee);

    EmployeeDto actualEmployee = employeeService.getEmployeeById(1L);

    assertThat(actualEmployee.getFirstname()).isEqualTo(mockedSavedEmployee.getFirstname());
    assertThat(actualEmployee.getLastname()).isEqualTo(mockedSavedEmployee.getLastname());

    verify(employeeRepository).findById(1L);
    verify(employeeMapper).convert(expectedEmployee);

  }

  @Test
  @DisplayName("Should return employee not found, when passed with invalid employee id")
  public void getOneEmployee_return_EmployeeNotFoundException() {

    Optional<Employee> expectedEmployee = Optional.empty();

    when(employeeRepository.findById(1L)).thenReturn(expectedEmployee);

    assertThrows(EmployeeNotFoundException.class,
        () -> employeeService.getEmployeeById(1L));

  }

  @Test
  @DisplayName("Should delete employee successfully")
  public void deleteEmployeeTest() {
    Employee expectedEmployee = new Employee();
    expectedEmployee.setId(1L);
    expectedEmployee.setFirstname("Given");
    expectedEmployee.setLastname("Sur");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));

    employeeService.deleteEmployeeById(expectedEmployee.getId());

    verify(employeeRepository).delete(expectedEmployee);
    verify(employeeRepository).findById(1L);

  }

  @Test
  @DisplayName("Should throw exception when deleting an employee while passing invalid id")
  public void deleteEmployee_return_EmployeeNotFoundException() {
    Optional<Employee> expectedEmployee = Optional.empty();

    when(employeeRepository.findById(1L)).thenReturn(expectedEmployee);

    assertThrows(EmployeeNotFoundException.class,
        () -> employeeService.deleteEmployeeById(1L));
  }

  @Test
  @DisplayName("Should update employee first details and return updated value")
  public void updateEmployeeTest() {

    Employee expectedEmployee = new Employee();
    expectedEmployee.setId(1L);
    expectedEmployee.setFirstname("LastNameBeforeUpdate");
    expectedEmployee.setLastname("FirstNameBeforeUpdate");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));

    EmployeeRequestDto updatePayload = new EmployeeRequestDto(
        expectedEmployee.getId(), "FirstNameAfterUpdate", "LastNameAfterUpdate");

    Employee mockedEmployeeEntityFromRepo = mock(Employee.class);
    when(employeeRepository.save(expectedEmployee)).thenReturn(mockedEmployeeEntityFromRepo);

    EmployeeDto mockedSavedEmployee = mock(EmployeeDto.class);
    when(employeeMapper.convert(mockedEmployeeEntityFromRepo)).thenReturn(mockedSavedEmployee);

    EmployeeDto actualUpdatedEmployee = employeeService.update(updatePayload);

    assertThat(actualUpdatedEmployee.getLastname()).isEqualTo(mockedSavedEmployee.getLastname());
    assertThat(actualUpdatedEmployee.getFirstname()).isEqualTo(mockedSavedEmployee.getFirstname());

    verify(employeeRepository).findById(1L);
    verify(employeeRepository).save(expectedEmployee);
    verify(employeeMapper).convert(mockedEmployeeEntityFromRepo);

  }

  @Test
  @DisplayName("Get page object containing 3 employee sorted by firstname descending")
  public void getPagedObjectContainingEmployeesSortedByFirstNameDesc() {
    EmployeeDto employee1 = new EmployeeDto();
    employee1.setId(1L);
    employee1.setFirstname("AAA1");
    employee1.setLastname("BBB1");
    EmployeeDto employee2 = new EmployeeDto();
    employee2.setId(2L);
    employee2.setFirstname("AAA2");
    employee2.setLastname("BBB1");
    EmployeeDto employee3 = new EmployeeDto();
    employee3.setId(3L);
    employee3.setFirstname("AAA3");
    employee3.setLastname("BBB1");

    Page<Employee> returnEmployees = new PageImpl(
        Arrays.asList(employee1, employee2, employee3));

    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "firstname"));

    when(employeeRepository.findAll(pageable))
        .thenReturn(returnEmployees);

    Page<EmployeeDto> mockedEmployeeDtoPage = mock(Page.class);
    when(employeeMapper.convert(returnEmployees)).thenReturn(mockedEmployeeDtoPage);

    Page<EmployeeDto> actualEmployeeDtoPage = employeeService.getEmployees(pageable);

    assertThat(actualEmployeeDtoPage.getTotalPages()).isEqualTo(
        mockedEmployeeDtoPage.getTotalPages());
    assertThat(actualEmployeeDtoPage.getTotalElements()).isEqualTo(
        mockedEmployeeDtoPage.getTotalElements());
    assertThat(actualEmployeeDtoPage.getNumberOfElements()).isEqualTo(
        mockedEmployeeDtoPage.getNumberOfElements());
    assertThat(actualEmployeeDtoPage.getContent()).isEqualTo(mockedEmployeeDtoPage.getContent());

    verify(employeeRepository).findAll(pageable);
    verify(employeeMapper).convert(returnEmployees);

  }


}
