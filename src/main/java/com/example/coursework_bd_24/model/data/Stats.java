package com.example.coursework_bd_24.model.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Stats {
    String name;
    Long countItems;

    List<Metrics> metricsList = new ArrayList<>();
}
