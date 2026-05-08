package ru.itis.dis403.lab_09.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class BenchmarkResult {
    private String type;
    private double averageMs;
    private int iterations;
}
