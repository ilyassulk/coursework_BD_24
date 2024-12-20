package com.example.coursework_bd_24.model.factory;

import com.example.coursework_bd_24.model.Table1;

public class Table1Factory {
    public static Table1 createDefaultByData(String data){
        Table1 table1 = new Table1();

        table1.setName(data);
        table1.setWeightData(WeightDataFactory.createDefaultByData(data));

        return table1;
    }
}
