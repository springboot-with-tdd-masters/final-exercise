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

	public Page<Skill> getAllBooks(Pageable pageable) {
		return skillRepository.findAll(pageable);
	}

	public Optional<Skill> getBookById(Long id) {
		return skillRepository.findById(id);
	}

	public Skill createOrUpdateBook(Skill entity) throws RecordNotFoundException {
		Optional<Skill> book = getBookById(entity.getId());
		if (book.isPresent()) {
			Skill newEntity = book.get();
			newEntity.setDescription(entity.getDescription());
			newEntity = skillRepository.save(newEntity);
			return newEntity;
		} else {
			entity = skillRepository.save(entity);
			return entity;
		}
	}

	public void deleteByUserId(Long id) throws RecordNotFoundException {
		Optional<Skill> book = getBookById(id);
		if (book.isPresent()) {
			skillRepository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No book record exist for given id");
		}
	}

}
