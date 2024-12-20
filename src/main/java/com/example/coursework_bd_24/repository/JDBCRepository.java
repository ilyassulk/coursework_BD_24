package com.example.coursework_bd_24.repository;

import com.example.coursework_bd_24.mapper.rowmapper.Table1EagerRowMapper;
import com.example.coursework_bd_24.mapper.rowmapper.Table1RowMapper;
import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.utility.sqlutility.Table1SQLFieldsUtil;
import com.example.coursework_bd_24.utility.sqlutility.Table2SQLFieldsUtil;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Repository
public class JDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Table1SQLFieldsUtil table1SQLUtil;
    private final Table2SQLFieldsUtil table2SQLUtil;

    public void save (Table1 item){
        String query = "INSERT INTO table_1 (" + String.join(",",table1SQLUtil.getStringNamesOfFields()) + ") VALUES (" + String.join(",",table1SQLUtil.getStringDataOfFields(item)) + ")";
        jdbcTemplate.execute(query);
    }

    public List<Table1> selectAll(){
        String query = "select * from table_1";
        return jdbcTemplate.query(query, new Table1RowMapper(""));
    }

    public List<Table1> selectByName (String name){
        String query = "select * from table_1 where name = ?";
        return jdbcTemplate.query(query, new Table1RowMapper(""), name);
    }

    public List<Table1> selectAllEager (){
        String query = "select "+"table_1.id as table_1_id, table_2.id as table_2_id,"+String.join(",", table1SQLUtil.getStringNamesOfFieldsWithAliasByTable("table_1"))+","+String.join(",", table2SQLUtil.getStringNamesOfFieldsWithAliasByTable("table_2"))+" from table_1 join table_2 on table_1.table_2_id = table_2.id";
        //String query = "select "+"*"+" from table_1";
        return jdbcTemplate.query(query, new Table1EagerRowMapper("table_1_", "table_2_"));
    }

    public void update(Table1 item) {
        List<String> fieldNames = table1SQLUtil.getStringNamesOfFields();
        List<String> fieldData = table1SQLUtil.getStringDataOfFields(item);

        String query = "UPDATE table_1 SET "
                + IntStream.range(0, fieldNames.size())
                .mapToObj(i -> fieldNames.get(i) + " = " + fieldData.get(i))
                .collect(Collectors.joining(", "))
                + " WHERE id = " + item.getId();

        jdbcTemplate.execute(query);
    }


    public void deleteAll(){
        String query = "delete from table_1 where TRUE";
        jdbcTemplate.execute(query);
    }
}
