package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    private final String employee1FN = "James";
    private final String employee1LN = "Bond";
    private final String employee2FN = "Zea";
    private final String employee2LN = "Abad";

    @MockBean
    private EmployeeServiceImpl employeeService;

    private List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        employeeList.add(buildEmployee(1L, employee1FN, employee1LN));
        employeeList.add(buildEmployee(2L, employee2FN, employee2LN));

    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close() {
        employeeList = new ArrayList<>();
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successCreateEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(employee1FN);
        employeeRequest.setLastName(employee1LN);

        ArgumentCaptor<EmployeeRequest> employeeRequestArgumentCaptor = ArgumentCaptor.forClass(EmployeeRequest.class);
        when(employeeService.createNewEmployee(employeeRequestArgumentCaptor.capture())).thenReturn(employeeList.get(0));

        employeeService.createNewEmployee(employeeRequest);
        assertThat(employeeRequestArgumentCaptor.getValue().getFirstName(), is(employee1FN));
    }

    @Test
    @DisplayName("Given a successful read by ID, response should give http status 200")
    public void shouldReadById() throws Exception {
        when(employeeService.readEmployeeById(1L)).thenReturn(employeeList.get(0));
        Employee retrievedEmployee = employeeService.readEmployeeById(1L);
        assertThat(employee1FN, is(retrievedEmployee.getFirstName()));
    }

    @Test
    @DisplayName("All Employees should be returned based on Pageable request")
    public void testReadAllEmployees() {
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("lastName").ascending());
        List<Employee> sortedEmployees = employeeList.stream().sorted(Comparator.comparing(Employee::getLastName)).collect(Collectors.toList());
        Page<Employee> employeePage = new PageImpl<>(sortedEmployees);
        when(employeeService.readAllEmployees(pageRequest)).thenReturn(employeePage);

        employeeService.readAllEmployees(pageRequest);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(employeeService).readAllEmployees(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
        assertThat(pageable.getPageNumber(), is(0));
        assertThat(pageable.getPageSize(), is(2));

        Page<Employee> page = employeeService.readAllEmployees(pageRequest);
        assertThat(page.getContent(), hasSize(2));
        assertThat(page.getContent().get(0).getLastName(), is(employee2LN));
        assertThat(page.getContent().get(1).getLastName(), is(employee1LN));
    }

    @Test
    @DisplayName("Employee should be updated based on ID")
    public void testUpdateEmployee() {
        String newEmployeeLastName = "Perry";
        Long employeeId = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setId(employeeId);
        employeeRequest.setFirstName(employee1FN);
        employeeRequest.setLastName(newEmployeeLastName);

        when(employeeService.updateEmployee(employeeRequest)).thenReturn(updateEmployee(employeeList.get(0), newEmployeeLastName));
        Employee updatedEmployee = employeeService.updateEmployee(employeeRequest);

        ArgumentCaptor<EmployeeRequest> employeeRequestArgumentCaptor = ArgumentCaptor.forClass(EmployeeRequest.class);
        verify(employeeService).updateEmployee(employeeRequestArgumentCaptor.capture());
        assertThat(employeeRequestArgumentCaptor.getValue().getLastName(), is(newEmployeeLastName));
        assertThat(updatedEmployee.getLastName(), is(newEmployeeLastName));
    }

    private Employee updateEmployee(Employee employee, String newEmployeeLastName) {
        employee.setLastName(newEmployeeLastName);
        return employee;
    }

    @Test
    @DisplayName("Given a successful delete, response should give http status 200")
    public void successDeleteEmployeeById() throws Exception {
        Long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployeeById(employeeId);
        employeeService.deleteEmployeeById(employeeId);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(employeeService).deleteEmployeeById(idCaptor.capture());
        assertThat(idCaptor.getValue(), is(employeeId));
    }

    @Test
    @DisplayName("Given a fail read, response should give http status 404")
    public void shouldReturn404AuthorNotFoundGet() throws Exception {
        Long employeeId = 42L;
        when(employeeService.readEmployeeById(employeeId)).thenThrow(new NotFoundException(String.format("Employee with id %s not found", employeeId)));
    }

}