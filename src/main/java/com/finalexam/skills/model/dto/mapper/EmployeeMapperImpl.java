package com.finalexam.skills.model.dto.mapper;

import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.entity.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapperImpl implements EmployeeMapper{

  @Override
  public EmployeeDto convert(Employee employee) {
    return new EmployeeDto(employee.getId(), employee.getFirstname(), employee.getLastname(),
        employee.getCreatedAt(), employee.getUpdatedAt());
  }

  @Override
  public List<EmployeeDto> convertToDtoList(List<Employee> employeeEntityList) {
    List<EmployeeDto> resultList = new ArrayList<>();
    for (Employee employee : employeeEntityList) {
      resultList.add(convert(employee));
    }
    return resultList;

  }

  @Override
  public Page<EmployeeDto> convert(Page<Employee> employeeEntityPage) {
    Page<EmployeeDto> result = new PageImpl<>(convertToDtoList(employeeEntityPage.getContent()),
        employeeEntityPage.getPageable(), employeeEntityPage.getSize());
    return result;

  }

  @Override
  public Employee convert(EmployeeDto employeeDto) {
    Employee employee = new Employee();
    employee.setId(employeeDto.getId());
    employee.setFirstname(employee.getFirstname());
    employee.setLastname(employeeDto.getLastname());
    employee.setCreatedAt(employeeDto.getCreatedAt());
    employee.setUpdatedAt(employee.getUpdatedAt());
    return employee;
  }

  @Override
  public List<Employee> convertToEntityList(List<EmployeeDto> employeeDtoList) {
    return null;
  }
}
