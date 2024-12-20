package com.example.coursework_bd_24.mapper.rowmapper;

import com.example.coursework_bd_24.model.Table1;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table1RowMapper implements RowMapper<Table1> {
    private final String prefix;

    public Table1RowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Table1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table1 table1 = new Table1();

        table1.setId(rs.getLong(prefix+"id"));
        table1.setName(rs.getString(prefix+"name"));
        table1.setWeightData(new WeightDataRowMapper(prefix).mapRow(rs, rowNum));
        table1.setTable2(null);

        return table1;
    }
}
