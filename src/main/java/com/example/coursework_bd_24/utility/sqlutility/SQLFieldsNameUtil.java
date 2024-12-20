package com.example.coursework_bd_24.utility.sqlutility;


import java.util.List;

public interface SQLFieldsNameUtil<T> {
    List<String> getStringNamesOfFields();
    List<String> getStringNamesOfFieldsWithAliasByTable(String table);
}
