package com.example.servercurs.lucene;

import com.example.servercurs.entities.Course;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
@Component
public class IndexCreator {
    private final String INDEX_DIRECTORY = "D:\\unik\\sem6\\курсовой\\code\\serverCurs\\index";


    public void indexCourses(List<Course> courses) {
        try {
            Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);

            for (Course course : courses) {
                Document document = new Document();
                document.add(new TextField("course_name", course.getCourse_name(), Field.Store.YES));
//                document.add(new TextField("nameLanguage", course.getId_language().getName_language(), Field.Store.YES));
//                document.add(new TextField("nameSkills", course.getId_skills().getName_skills(), Field.Store.YES));
                indexWriter.addDocument(document);
            }

            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
