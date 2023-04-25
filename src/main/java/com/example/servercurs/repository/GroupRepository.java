package com.example.servercurs.repository;

import com.example.servercurs.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("from Group gr order by gr.id_group")
    List<Group> findAllOrder();
}
