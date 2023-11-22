package com.example.servercurs.repository;

import com.example.servercurs.entities.LessonsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsHistoryRepository extends JpaRepository<LessonsHistory, Integer> {
}
