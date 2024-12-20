package com.example.coursework_bd_24.mapper.rowmapper;

import com.example.coursework_bd_24.model.Table1;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table1EagerRowMapper implements RowMapper<Table1> {
    private final String table1Prefix;
    private final String table2Prefix;

    public Table1EagerRowMapper(String table1Prefix, String table2Prefix) {
        this.table1Prefix = table1Prefix;
        this.table2Prefix = table2Prefix;
    }

    @Override
    public Table1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table1 table1 = new Table1();

        table1.setId(rs.getLong(table1Prefix +"id"));
        table1.setName(rs.getString(table1Prefix +"name"));
        table1.setWeightData(new WeightDataRowMapper(table1Prefix).mapRow(rs, rowNum));
        table1.setTable2(new Table2RowMapper(table2Prefix).mapRow(rs, rowNum));

        return table1;
    }
}
