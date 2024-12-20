package com.example.coursework_bd_24.controller;

import com.example.coursework_bd_24.model.data.Metrics;
import com.example.coursework_bd_24.model.data.Stats;
import com.example.coursework_bd_24.service.enums.DatabaseType;
import com.example.coursework_bd_24.service.impl.StatService;
import com.example.coursework_bd_24.utility.UtilityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ShellComponent
public class ShellController {

    private final StatService statService;
    private final UtilityLogService utilityLogService;

    @Autowired
    public ShellController(StatService statService, UtilityLogService utilityLogService) {
        this.statService = statService;
        this.utilityLogService = utilityLogService;
    }

    /**
     * Пример использования:
     * run -l -t ALL 1000 2000 3000
     * run JPA 5000
     * run -t JDBC 1000 2000
     */
    @ShellMethod(value = "Run database operations with flags and multiple record counts. Flags: -l for logs, -t for tables, DB type: JPA/JDBC/JPA_N1/JPA_BATCH/ALL", key = "run")
    public String run(
            @ShellOption(help = "Flags (e.g. l t)", defaultValue = "") String flags,
            @ShellOption(help = "Database type: JPA, JDBC or ALL") String dbType,
            @ShellOption(help = "List of records counts to process", arity = 1) List<Long> countItemsList
    ) {
        boolean withLogs = flags.contains("l");
        boolean produceTables = flags.contains("t");

        // Определяем какие типы БД будем запускать
        List<DatabaseType> dbTypesToRun = new ArrayList<>();
        if ("ALL".equalsIgnoreCase(dbType)) {
            dbTypesToRun.add(DatabaseType.JPA);
            dbTypesToRun.add(DatabaseType.JDBC);
            //dbTypesToRun.add(DatabaseType.JPA_N1);
            dbTypesToRun.add(DatabaseType.JPA_BATCH);
        } else {
            try {
                dbTypesToRun.add(DatabaseType.valueOf(dbType.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return "Invalid database type. Please specify 'JPA', 'JDBC', 'JPA_N1', 'JPA_BATCH' or 'ALL'.";
            }
        }

        // Структура: operationName -> dbTypeName -> countItems -> Metrics
        Map<String, Map<String, Map<Long, Metrics>>> resultsMap = new LinkedHashMap<>();

        try {
            // Прогоняем для всех типов БД и всех наборов countItems
            for (DatabaseType dt : dbTypesToRun) {
                for (Long count : countItemsList) {
                    Stats stats = statService.doStat(count, dt, withLogs);
                    String statsDbName = stats.getName();

                    for (Metrics m : stats.getMetricsList()) {
                        resultsMap
                                .computeIfAbsent(m.name(), k -> new LinkedHashMap<>())
                                .computeIfAbsent(statsDbName, k -> new LinkedHashMap<>())
                                .put(count, m);
                    }
                }
            }

            // Если флаг -t установлен, генерируем таблицы через UtilityLogService
            if (produceTables) {
                utilityLogService.generateAndPrintTables(resultsMap);
            }

            return "Operations completed successfully!";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }
}


