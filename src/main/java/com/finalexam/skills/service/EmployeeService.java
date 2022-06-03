package com.finalexam.skills.service;

import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.dto.request.EmployeeRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

  EmployeeDto save(EmployeeRequestDto employeeRequest);

  EmployeeDto getEmployeeById(long id);

  void deleteEmployeeById(Long id);

  EmployeeDto update(EmployeeRequestDto updatePayload);

  Page<EmployeeDto> getEmployees(Pageable pageable);
}
