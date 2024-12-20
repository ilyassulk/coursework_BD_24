package com.example.coursework_bd_24.service;

import com.example.coursework_bd_24.model.Table1;

import java.util.List;

public interface DBService {
    void saveItem(Table1 item);
    List<Table1> selectAll();
    List<Table1> selectWhere(String name);
    List<Table1> selectAllWithJoin();
    void updateItem(Table1 item);
    void removeAllItems();
}
