package com.example.servercurs.service;

import com.example.servercurs.entities.Course;
import com.example.servercurs.lucene.CourseSearcher;
import com.example.servercurs.lucene.IndexCreator;
import com.example.servercurs.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CourseSearchService {
    private final IndexCreator courseIndexer;
    private final CourseSearcher courseSearcher;
    private final CourseService courseService;

    public void indexCourses() {
        List<Course> courses = courseService.findAllCourse();
        courseIndexer.indexCourses(courses);
    }

    public Set<Course> searchCourses(String queryText) {
        return courseSearcher.searchCourses(queryText);
    }

}
