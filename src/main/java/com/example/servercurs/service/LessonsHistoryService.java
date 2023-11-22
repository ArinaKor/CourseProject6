package com.example.servercurs.service;

import com.example.servercurs.entities.LessonsHistory;
import com.example.servercurs.repository.LessonsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonsHistoryService {
    private final LessonsHistoryRepository lessonsHistoryRepository;

    public List<LessonsHistory> findAllCourse() {
        return lessonsHistoryRepository.findAll();
    }

    public LessonsHistory findById(int id) {
        return lessonsHistoryRepository.findById(id).orElse(null);
    }

    public LessonsHistory save(LessonsHistory lesson) {
        return lessonsHistoryRepository.save(lesson);
    }

    public void delete(int id) {
        lessonsHistoryRepository.deleteById(id);
    }
}
