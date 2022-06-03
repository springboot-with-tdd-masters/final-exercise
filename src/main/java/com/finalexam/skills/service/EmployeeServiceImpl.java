package com.finalexam.skills.service;

import com.finalexam.skills.exception.EmployeeNotFoundException;
import com.finalexam.skills.model.dto.mapper.EmployeeMapper;
import com.finalexam.skills.model.dto.request.EmployeeRequestDto;
import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.entity.Employee;
import com.finalexam.skills.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  EmployeeMapper employeeMapper;

  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  public EmployeeDto save(EmployeeRequestDto employeeRequest) {
    Employee newEmployeeEntity = new Employee(
        employeeRequest.getFirstname(),
        employeeRequest.getLastname());

    Employee savedEmployee = employeeRepository.save(newEmployeeEntity);

    return employeeMapper.convert(savedEmployee);
  }

  @Override
  public EmployeeDto getEmployeeById(long id) {
    Employee employeeEntity =
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());

    return employeeMapper.convert(employeeEntity);
  }

  @Override
  public void deleteEmployeeById(Long id) {
    Employee employeeEntity =
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
    employeeRepository.delete(employeeEntity);
  }

  @Override
  public EmployeeDto update(EmployeeRequestDto updatePayload) {
    Employee updatedEmployeeEntity =
        employeeRepository.findById(updatePayload.getId())
            .orElseThrow(() -> new EmployeeNotFoundException());

    updatedEmployeeEntity.setFirstname(updatePayload.getFirstname());
    updatedEmployeeEntity.setLastname(updatePayload.getLastname());

    Employee savedEmployee = employeeRepository.save(updatedEmployeeEntity);

    return employeeMapper.convert(savedEmployee);
  }

  @Override
  public Page<EmployeeDto> getEmployees(Pageable pageable) {

    Page<Employee> employeeEntityPage = employeeRepository.findAll(pageable);

    return employeeMapper.convert(employeeEntityPage);
  }

}
