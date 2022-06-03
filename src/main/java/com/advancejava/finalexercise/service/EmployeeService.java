package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee request);

    String deleteEmployee(Long id);

    Page<Employee> getWithPaginationAndSort(int page, int limit, String field, String order);

//    Page<Account> getAccountsWithPaginationAndSort(int offset, int pageSize, String field, String order);
//    Page<BankTransaction> getBankTxnsWithPaginationAndSort(int page, int limit, String field, String order);

}
