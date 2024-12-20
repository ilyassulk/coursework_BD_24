package com.example.coursework_bd_24.service.impl;

import com.example.coursework_bd_24.model.data.Metrics;
import com.example.coursework_bd_24.model.data.Stats;
import com.example.coursework_bd_24.service.enums.DatabaseType;
import com.example.coursework_bd_24.utility.DBUtilityService;
import com.example.coursework_bd_24.utility.StatUtilityService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class StatService {
    private final DoService doService;
    private final DBUtilityService DBUtilityService;
    private final StatUtilityService statUtilityService;
    private final EntityManager entityManager;

    public StatService(DoService doService, DBUtilityService DBUtilityService, StatUtilityService statUtilityService, EntityManager entityManager) {
        this.doService = doService;
        this.DBUtilityService = DBUtilityService;
        this.statUtilityService = statUtilityService;
        this.entityManager = entityManager;
    }

    public Stats doStat(Long countItems, DatabaseType dbType, boolean withLogs) {
        Stats stats = new Stats();
        switch (dbType){
            case JPA -> {
                stats.setName("JPA");
            }
            case JDBC -> {
                stats.setName("JDBC");
            }
            case JPA_N1 -> {
                stats.setName("JPA N+1");
            }
            case JPA_BATCH -> {
                stats.setName("JPA BATCH");
            }
        }
        stats.setCountItems(countItems);
        
        boolean disableFirstLevelCaching = true;

        System.out.println(stats.getName() + " " + stats.getCountItems() + " ---------------------------------------");

        doService.setDatabaseType(dbType);


        if(withLogs)System.out.println("Inserting records...");
        if(disableFirstLevelCaching)entityManager.clear();
        Metrics metrics = doService.doInsert(countItems);
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Insert", metrics);
        stats.getMetricsList().add(metrics);

        DBUtilityService.connectTable2();

        if(withLogs)System.out.println("Selecting records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doSelect();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Select", metrics);
        stats.getMetricsList().add(metrics);

        if(withLogs)System.out.println("Selecting with WHERE (no exist) records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doSelectWhereNoExist();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Select with WHERE (no exist)", metrics);
        stats.getMetricsList().add(metrics);

        if(withLogs)System.out.println("Selecting with WHERE (all) records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doSelectWhereAll();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Select with WHERE (all)", metrics);
        stats.getMetricsList().add(metrics);

        if(withLogs)System.out.println("Selecting with JOIN records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doSelectJoin();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Select with JOIN", metrics);
        stats.getMetricsList().add(metrics);

        if(withLogs)System.out.println("Updating records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doUpdate();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Update", metrics);
        stats.getMetricsList().add(metrics);

        DBUtilityService.freeTable2();

        if(withLogs)System.out.println("Deleting records...");
        if(disableFirstLevelCaching)entityManager.clear();
        metrics = doService.doDelete();
        metrics = preparePerformance(metrics);
        if(withLogs)logPerformance("Delete", metrics);
        stats.getMetricsList().add(metrics);

        return stats;
    }

    private Metrics preparePerformance(Metrics startMetrics){
        long startCpuTime = startMetrics.cpuTime();
        long startTime = startMetrics.elapsedTime();

        long currentCpuTime = statUtilityService.getProcessCpuTime();
        long currentTime = statUtilityService.getCurrentTimeMillis();

        long elapsedCpuTime = currentCpuTime - startCpuTime;
        long elapsedTime = currentTime - startTime;

        return new Metrics(startMetrics.name(), elapsedTime, elapsedCpuTime);
    }

    private void logPerformance(String operation, Metrics startMetrics) {
        System.out.printf("%s operation took %d ms (CPU: %d ns)%n",
                operation, startMetrics.elapsedTime(), startMetrics.cpuTime());
    }
}

//run l t  --dbType ALL --countItemsList 100,200,300,400,500,600,700,800,900,1000