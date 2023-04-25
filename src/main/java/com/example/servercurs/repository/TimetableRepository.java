package com.example.servercurs.repository;

import com.example.servercurs.entities.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable, Integer> {
}
