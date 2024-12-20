package com.example.coursework_bd_24.utility.sqlutility;


import java.util.List;

public interface SQLFieldsDataUtil<T> {
    List<String> getStringDataOfFields(T item);
}
