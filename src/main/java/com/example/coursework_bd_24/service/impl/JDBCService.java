package com.example.coursework_bd_24.service.impl;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.repository.JDBCRepository;
import com.example.coursework_bd_24.service.DBService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JDBCService implements DBService {

    private final JDBCRepository jdbcRepository;

    @Override
    public void saveItem(Table1 item) {
        jdbcRepository.save(item);
    }

    @Override
    public List<Table1> selectAll() {
        return jdbcRepository.selectAll();
    }

    @Override
    public List<Table1> selectWhere(String name) {
        return jdbcRepository.selectByName(name);
    }

    @Override
    public List<Table1> selectAllWithJoin() {
        return jdbcRepository.selectAllEager();
    }

    @Override
    public void updateItem(Table1 item) {
        jdbcRepository.update(item);
    }

    @Override
    public void removeAllItems() {
        jdbcRepository.deleteAll();
    }
}
