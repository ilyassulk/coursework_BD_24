package com.example.coursework_bd_24.utility;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.model.Table2;
import com.example.coursework_bd_24.model.factory.WeightDataFactory;
import com.example.coursework_bd_24.repository.Table1JPARepository;
import com.example.coursework_bd_24.repository.Table2JPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DBUtilityService {
    private final Table1JPARepository table1JPARepository;
    private final Table2JPARepository table2JPARepository;

    @Transactional
    public void connectTable2(){
        List<Table1> table1s = table1JPARepository.findAll();
        for (Table1 table1 : table1s) {
            Table2 table2 = new Table2();
            table2.setWeightData(WeightDataFactory.createDefaultByData("connected_table2"));
            table1.setTable2(table2);
            table2.setTable1(table1);
        }
        table1JPARepository.saveAll(table1s);
    }

    @Transactional
    public void freeTable2(){
        table2JPARepository.deleteAll();
    }
}
