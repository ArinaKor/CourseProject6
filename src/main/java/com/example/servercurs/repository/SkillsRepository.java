package com.example.servercurs.repository;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    /*@Query("From Skills l Where l.name_skills =: skills")
    Skills findByName_skills(String skills);*/
    @Query("from Skills pl WHERE pl.name_skills = :name")
    Skills findSkillsByName_skills(String name);
}
