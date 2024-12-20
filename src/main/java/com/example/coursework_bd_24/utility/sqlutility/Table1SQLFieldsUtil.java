package com.example.coursework_bd_24.utility.sqlutility;

import com.example.coursework_bd_24.model.Table1;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Table1SQLFieldsUtil implements SQLFieldsNameUtil<Table1>, SQLFieldsDataUtil<Table1> {

    private final WeightDataSQLFieldsUtil weightDataSQLUtil;

    @Override
    public List<String> getStringNamesOfFields() {
        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.addAll(weightDataSQLUtil.getStringNamesOfFields());
        return fields;
    }

    @Override
    public List<String> getStringDataOfFields(Table1 table1) {
        List<String> fields = new ArrayList<>();
        fields.add(table1.getName() != null ? "\'" + table1.getName() + "\'" : "NULL");
        fields.addAll(weightDataSQLUtil.getStringDataOfFields(table1.getWeightData()));
        return fields;
    }

    @Override
    public List<String> getStringNamesOfFieldsWithAliasByTable(String table) {
        List<String> fields = this.getStringNamesOfFields();
        return fields.stream().map(field -> table + "." + field + " AS " + table + "_" + field).toList();
    }
}
