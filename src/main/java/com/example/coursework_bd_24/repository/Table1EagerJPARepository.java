package com.example.coursework_bd_24.repository;

import com.example.coursework_bd_24.model.Table1;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Table1EagerJPARepository extends JpaRepository<Table1, Long> {
    @EntityGraph(attributePaths = {"table2"})
    List<Table1> findAll();
}