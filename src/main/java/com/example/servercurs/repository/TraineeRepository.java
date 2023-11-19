package com.example.servercurs.repository;

import com.example.servercurs.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    @Query("SELECT DISTINCT course FROM Trainee course " +
            "JOIN FETCH course.id_skills id_skills JOIN FETCH course.id_language id_language")
    List<Trainee> findWithAll();

    @Query("from Trainee t where t.id_language.id_language = :id_language and t.id_skills.id_skills = :id_skills")
    List<Trainee> findTraineesByLanguageAndSkill(@Param("id_language") int id_language, @Param("id_skills") int id_skills);
}
