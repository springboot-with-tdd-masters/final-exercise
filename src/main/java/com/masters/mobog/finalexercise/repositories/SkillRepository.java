package com.masters.mobog.finalexercise.repositories;

import com.masters.mobog.finalexercise.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
