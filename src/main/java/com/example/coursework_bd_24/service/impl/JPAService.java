package com.example.coursework_bd_24.service.impl;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.repository.Table1EagerJPARepository;
import com.example.coursework_bd_24.repository.Table1JPARepository;
import com.example.coursework_bd_24.service.DBService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JPAService implements DBService {

    private final Table1JPARepository table1JPARepository;
    private final Table1EagerJPARepository table1EagerJPARepository;

    @Override
    public void saveItem(Table1 item) {
        table1JPARepository.save(item);
    }

    @Override
    public List<Table1> selectAll() {
        return table1JPARepository.findAll();
    }

    @Override
    public List<Table1> selectWhere(String name) {
        return table1JPARepository.findAllByName(name);
    }

    @Override
    public List<Table1> selectAllWithJoin() {
        return table1EagerJPARepository.findAll();
    }

    @Override
    public void updateItem(Table1 item) {
        table1JPARepository.save(item);
    }

    @Override
    public void removeAllItems() {
        table1JPARepository.deleteAll();
    }
}
