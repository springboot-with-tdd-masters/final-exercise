package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.helper.CustomErrorResponse;
import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.model.Skill;
import com.advancejava.finalexercise.repository.EmployeeRepository;
import com.advancejava.finalexercise.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl extends CustomErrorResponse implements SkillService {

    @Autowired
    SkillRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Skill addSkill(Skill skill, Long id) {
        //get employee by id
        Employee employee = new Employee();
        if (employeeRepository.findById(id).isPresent()){
             employee = employeeRepository.findById(id).get();
        }else{
            employee.setId(1L);
        }
        skill.setEmployee(employee);

        return repository.save(skill);
    }

    @Override
    public Skill getSkill(Long id) {
        if (repository.findById(id).isEmpty()){
            throw badRequest("NotFound");
        }
        return repository.findById(id).get();
    }

    @Override
    public List<Skill> getAllSkills() {
        if (repository.findAll().isEmpty()){
            throw badRequest("NotFound");
        }
        return repository.findAll();
    }

    @Override
    public Skill updateSkill(Skill request, Long empId, Long id) {
        Employee employee = new Employee();
        if (employeeRepository.findById(empId).isPresent()) {
            employee = employeeRepository.findById(empId).get();
        } else {
            employee.setId(1L);
        }
        request.setEmployee(employee);
        request.setId(id);

        return repository.save(request);
    }

    @Override
    public String deleteSkill(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw badRequest("NotFound");
        }
        repository.deleteById(id);
        return "Success";
    }

    @Override
    public Page<Skill> getWithPaginationAndSort(int page, int limit, String field, String order) {
        Sort sOrder = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        return repository.findAll(PageRequest.of(page, limit, sOrder));
    }

}
