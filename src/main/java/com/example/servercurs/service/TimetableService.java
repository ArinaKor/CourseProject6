package com.example.servercurs.service;

import com.example.servercurs.entities.TimeTable;
import com.example.servercurs.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TimetableRepository timetableRepository;

    public List<TimeTable> findAllTimeTables() {
        return timetableRepository.findAll();
    }

    public TimeTable findById(int id) {
        return timetableRepository.findById(id).orElse(null);
    }

    public TimeTable save(TimeTable timetable) {
        return timetableRepository.save(timetable);
    }

    public void delete(int id) {
        timetableRepository.deleteById(id);
    }
}
