package ru.itis.dis403.lab_09.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/benchmark")
public class BenchmarkController {

    @GetMapping
    public String benchmarkPage() {
        return "benchmark";
    }

}