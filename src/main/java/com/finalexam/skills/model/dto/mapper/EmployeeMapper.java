package com.finalexam.skills.model.dto.mapper;

import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.entity.Employee;
import java.util.List;
import org.springframework.data.domain.Page;

public interface EmployeeMapper {

  EmployeeDto convert(Employee employee);

  List<EmployeeDto> convertToDtoList(List<Employee> employeeEntityList);

  Page<EmployeeDto> convert(Page<Employee> employeeEntityPage);

  Employee convert(EmployeeDto employeeDto);

  List<Employee> convertToEntityList(List<EmployeeDto> employeeDtoList);

}
