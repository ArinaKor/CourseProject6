package com.example.servercurs.repository;

import com.example.servercurs.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    /*@Query("From Language l Where l.name_language =: name_language")
    Language findByName_language(String name_language);*/
    @Query("from Language pl WHERE pl.name_language = :name")
    Language findLanguageByName_language(String name);
}
