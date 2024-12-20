package com.example.coursework_bd_24.utility;

import org.springframework.stereotype.Component;

@Component
public class StatUtilityService {
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long getProcessCpuTime() {
        return java.lang.management.ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    public long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
