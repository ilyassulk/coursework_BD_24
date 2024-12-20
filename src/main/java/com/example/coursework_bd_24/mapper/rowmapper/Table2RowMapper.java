package com.example.coursework_bd_24.mapper.rowmapper;

import com.example.coursework_bd_24.model.Table2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table2RowMapper implements RowMapper<Table2> {
    private final String prefix;

    public Table2RowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Table2 mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table2 table2 = new Table2();

        table2.setId(rs.getLong(prefix+"id"));
        table2.setWeightData(new WeightDataRowMapper(prefix).mapRow(rs, rowNum));

        return table2;
    }
}
