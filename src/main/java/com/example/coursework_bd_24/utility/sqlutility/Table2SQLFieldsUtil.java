package com.example.coursework_bd_24.utility.sqlutility;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.model.Table2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Table2SQLFieldsUtil implements SQLFieldsNameUtil<Table2>, SQLFieldsDataUtil<Table2> {

    private final WeightDataSQLFieldsUtil weightDataSQLUtil;

    @Override
    public List<String> getStringNamesOfFields() {
        return new ArrayList<>(weightDataSQLUtil.getStringNamesOfFields());
    }

    @Override
    public List<String> getStringDataOfFields(Table2 table2) {
        return new ArrayList<>(weightDataSQLUtil.getStringDataOfFields(table2.getWeightData()));
    }

    @Override
    public List<String> getStringNamesOfFieldsWithAliasByTable(String table) {
        List<String> fields = this.getStringNamesOfFields();
        return fields.stream().map(field -> table + "." + field + " AS " + table + "_" + field).toList();
    }
}
