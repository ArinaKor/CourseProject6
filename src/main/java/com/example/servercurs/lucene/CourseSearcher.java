package com.example.servercurs.lucene;

import com.example.servercurs.entities.Course;
import com.example.servercurs.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CourseSearcher {
    private final String INDEX_DIRECTORY = "D:\\unik\\sem6\\курсовой\\code\\serverCurs\\index";
    private final CourseService courseService;

    public Set<Course> searchCourses(String queryText) {
        Set<Course> courses = new HashSet<>();

        try {
            Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new RussianAnalyzer();
            QueryParser queryParser = new QueryParser("course_name", analyzer);
            Query query = queryParser.parse(queryText);

            TopDocs topDocs = indexSearcher.search(query, 10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc scoreDoc : scoreDocs) {
                Document document = indexSearcher.doc(scoreDoc.doc);
                String name = document.get("course_name");/*
                String nameLanguage = document.get("nameLanguage");
                String nameSkills = document.get("nameSkills");*/

                Course course = courseService.findCourseByCourse_name(name);
                courses.add(course);
            }

            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}