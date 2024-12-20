package com.example.coursework_bd_24.model.factory;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.model.Table2;

public class Table2Factory {
    public static Table2 createDefaultByData(String data){
        Table2 table2 = new Table2();

        table2.setWeightData(WeightDataFactory.createDefaultByData(data));

        return table2;
    }
}
