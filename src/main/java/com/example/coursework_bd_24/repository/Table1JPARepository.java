package com.example.coursework_bd_24.repository;

import com.example.coursework_bd_24.model.Table1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Table1JPARepository extends JpaRepository<Table1, Long> {
    List<Table1> findAllByName(String name);
}