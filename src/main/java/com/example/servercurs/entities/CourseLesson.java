package com.example.servercurs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "course_lesson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseLesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lesson")
    private Integer idLesson;

    @Column(name = "lesson_name")
    private String lessonName;

    @Lob
    @Column(name = "links")
    private String links;

    @Column(name = "lesson_text", columnDefinition = "LONGTEXT")
    private String lessonText;

    @Column(name = "number_lesson")
    private int numberLesson;

    @OneToMany(mappedBy = "idHistory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LessonsHistory> lessonsHistories;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_course", nullable = false)
    @JsonIgnore
    private Course id_course;

    public void setLinks(List<String> links) {
        try {
            this.links = new ObjectMapper().writeValueAsString(links);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getLinks(){
        try {
            return new ObjectMapper().readValue(this.links, new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
