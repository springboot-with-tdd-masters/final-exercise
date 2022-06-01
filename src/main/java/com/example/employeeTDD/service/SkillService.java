package com.example.employeeTDD.service;

import java.util.Optional;

import com.example.employeeTDD.exception.RecordNotFoundException;
import com.example.employeeTDD.model.Skill;
import com.example.employeeTDD.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

	@Autowired
	SkillRepository skillRepository;

	public Page<Skill> getAllSkills(Pageable pageable) {
		return skillRepository.findAll(pageable);
	}

	public Optional<Skill> getSkillById(Long id) {
		return skillRepository.findById(id);
	}

	public Skill createOrUpdateSkill(Skill entity) throws RecordNotFoundException {
		Optional<Skill> skill = getSkillById(entity.getId());
		if (skill.isPresent()) {
			Skill newEntity = skill.get();
			newEntity.setDescription(entity.getDescription());
			newEntity.setDuration(entity.getDuration());
			newEntity.setLastUsed(entity.getLastUsed());
			newEntity = skillRepository.save(newEntity);
			return newEntity;
		} else {
			entity = skillRepository.save(entity);
			return entity;
		}
	}

	public void deleteByUserId(Long id) throws RecordNotFoundException {
		Optional<Skill> book = getSkillById(id);
		if (book.isPresent()) {
			skillRepository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No book record exist for given id");
		}
	}

}
