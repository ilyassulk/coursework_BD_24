package com.example.coursework_bd_24.service.impl;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.repository.Table1JPARepository;
import com.example.coursework_bd_24.service.DBService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JPABatchService implements DBService {

    private final Table1JPARepository table1JPARepository;


    @Override
    public void saveItem(Table1 item) {
        table1JPARepository.save(item);
    }

    @Override
    public List<Table1> selectAll() { return table1JPARepository.findAll(); }

    @Override
    public List<Table1> selectWhere(String name) {
        return List.of();
    }

    @Override
    public List<Table1> selectAllWithJoin() {
        return List.of();
    }

    @Override
    public void updateItem(Table1 item) {}

    @Override
    public void removeAllItems() {
        table1JPARepository.deleteAll();
    }
}
