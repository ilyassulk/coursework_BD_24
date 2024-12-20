package com.example.coursework_bd_24.utility;

import com.example.coursework_bd_24.model.data.Metrics;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class UtilityLogService {

    /**
     * Генерирует таблицы CSV для каждой операции (для elapsedTime и cpuTime),
     * выводит их в консоль и записывает в файлы.
     *
     * @param resultsMap Структура данных:
     *                   operationName ->
     *                      dbTypeName ->
     *                         countItems -> Metrics
     */
    public void generateAndPrintTables(Map<String, Map<String, Map<Long, Metrics>>> resultsMap) {
        // Собираем наборы операций, типов БД, количеств записей
        Set<String> operations = resultsMap.keySet();
        Set<String> dbNames = new LinkedHashSet<>();
        Set<Long> allCounts = new LinkedHashSet<>();

        for (Map.Entry<String, Map<String, Map<Long, Metrics>>> opEntry : resultsMap.entrySet()) {
            dbNames.addAll(opEntry.getValue().keySet());
            for (Map<Long, Metrics> counts : opEntry.getValue().values()) {
                allCounts.addAll(counts.keySet());
            }
        }

        List<Long> sortedCounts = new ArrayList<>(allCounts);
        sortedCounts.sort(Long::compare);

        // Создаём директорию для логов (при необходимости)
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        try {
            for (String operation : operations) {
                // Генерация таблицы для elapsedTime
                String elapsedCsv = buildCsvTable("elapsedTime", operation, dbNames, sortedCounts, resultsMap, true);
                // Генерация таблицы для cpuTime
                String cpuCsv = buildCsvTable("cpuTime", operation, dbNames, sortedCounts, resultsMap, false);

                // Вывод в консоль
                System.out.println(elapsedCsv);
                System.out.println(cpuCsv);

                // Запись в файлы
                writeFile(new File(logsDir, "elapsedTime_" + operation + ".csv"), elapsedCsv);
                writeFile(new File(logsDir, "cpuTime_" + operation + ".csv"), cpuCsv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Формирует CSV-таблицу для конкретной метрики (elapsedTime или cpuTime).
     *
     * @param metricType "elapsedTime" или "cpuTime"
     * @param operation название операции
     * @param dbNames набор типов БД
     * @param sortedCounts отсортированный список количеств записей
     * @param resultsMap данные метрик
     * @param isElapsed если true, используется elapsedTime, если false — cpuTime
     * @return строка с CSV таблицей
     */
    private String buildCsvTable(String metricType,
                                 String operation,
                                 Set<String> dbNames,
                                 List<Long> sortedCounts,
                                 Map<String, Map<String, Map<Long, Metrics>>> resultsMap,
                                 boolean isElapsed) {
        StringBuilder sb = new StringBuilder();
        sb.append(metricType).append("_").append(operation).append(".csv\n");
        sb.append("type");
        for (Long c : sortedCounts) {
            sb.append(";").append(c);
        }
        sb.append("\n");

        for (String dbN : dbNames) {
            sb.append(dbN);
            for (Long c : sortedCounts) {
                Metrics met = resultsMap.get(operation).getOrDefault(dbN, Collections.emptyMap()).get(c);
                long value = -1;
                if (met != null) {
                    value = isElapsed ? met.elapsedTime() : met.cpuTime();
                }
                sb.append(";").append(value);
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Записывает данные в файл
     */
    private void writeFile(File file, String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(data);
        }
    }
}
