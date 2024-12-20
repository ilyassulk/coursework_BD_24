package com.example.coursework_bd_24.service.impl;

import com.example.coursework_bd_24.model.Table1;
import com.example.coursework_bd_24.model.data.Metrics;
import com.example.coursework_bd_24.model.factory.WeightDataFactory;
import com.example.coursework_bd_24.service.DBService;
import com.example.coursework_bd_24.service.enums.DatabaseType;
import com.example.coursework_bd_24.utility.StatUtilityService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DoService {

    private final JPAService jpaService;
    private final JDBCService jdbcService;
    private final JPAN1Service jpan1Service;
    private final JPABatchService jpabatchService;
    private final StatUtilityService statUtilityService;

    @Setter
    private DatabaseType databaseType;

    public DoService(JPAService jpaService, JDBCService jdbcService, JPAN1Service jpan1Service, JPABatchService jpabatchService, StatUtilityService statUtilityService) {
        this.jpaService = jpaService;
        this.jdbcService = jdbcService;
        this.jpan1Service = jpan1Service;
        this.jpabatchService = jpabatchService;
        this.statUtilityService = statUtilityService;
    }

    public Metrics doInsert(Long countItems) {
        DBService service = getService(databaseType);
        List<Table1> items = new ArrayList<>();
        for (int i = 0; i < countItems; i++) {
            Table1 item = new Table1();
            item.setName("insert");
            item.setWeightData(WeightDataFactory.createDefaultByData("insert"));
            items.add(item);
        }

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        for (Table1 item : items) {
            service.saveItem(item);
        }

        return new Metrics("insert",startTime, startCPU);
    }

    public Metrics doSelect() {
        DBService service = getService(databaseType);

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        List<Table1> items = service.selectAll();

        return new Metrics("select",startTime, startCPU);
    }

    public Metrics doSelectWhereNoExist() {
        DBService service = getService(databaseType);
        String name = "doesn't exist";

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        List<Table1> items = service.selectWhere(name);

        return new Metrics("select_where_no_exist",startTime, startCPU);
    }

    public Metrics doSelectWhereAll() {
        DBService service = getService(databaseType);
        String name = "insert";

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        List<Table1> items = service.selectWhere(name);

        return new Metrics("select_where_all",startTime, startCPU);
    }

    public Metrics doSelectJoin() {
        DBService service = getService(databaseType);

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        List<Table1> items = service.selectAllWithJoin();

        return new Metrics("select_join",startTime, startCPU);
    }

    public Metrics doUpdate() {
        DBService service = getService(databaseType);
        List<Table1> items = service.selectAll();
        for (Table1 item : items) {
            item.setName("update");
            item.setWeightData(WeightDataFactory.createDefaultByData("update"));
        }

        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        for (Table1 item : items) {
            service.updateItem(item);
        }

        return new Metrics("update",startTime, startCPU);
    }

    public Metrics doDelete() {
        DBService service = getService(databaseType);
        long startTime = statUtilityService.getCurrentTimeMillis();
        long startCPU = statUtilityService.getProcessCpuTime();

        service.removeAllItems();

        return new Metrics("delete",startTime, startCPU);
    }

    private DBService getService(DatabaseType dbType) {
        return switch (dbType) {
            case JPA -> jpaService;
            case JDBC -> jdbcService;
            case JPA_N1 -> jpan1Service;
            case JPA_BATCH -> jpabatchService;
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };
    }
}
