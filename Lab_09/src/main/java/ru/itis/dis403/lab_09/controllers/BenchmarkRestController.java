package ru.itis.dis403.lab_09.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dis403.lab_09.dto.BenchmarkResult;
import ru.itis.dis403.lab_09.service.ImageService;
import ru.itis.dis403.lab_09.service.ImageServiceForNats;

@RestController
@RequestMapping("/api/benchmark")
public class BenchmarkRestController {

    private static final int ITERATIONS = 10;
    private static final int WARMUP = 1;

    private final ImageService imageService;
    private final ImageServiceForNats imageServiceForNats;

    public BenchmarkRestController(ImageService imageService, ImageServiceForNats imageServiceForNats) {
        this.imageService = imageService;
        this.imageServiceForNats = imageServiceForNats;
    }

    @PostMapping("/http")
    public BenchmarkResult benchmarkHttp(@RequestParam("image") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();

            long total = 0;

            for (int i = 0; i < ITERATIONS + WARMUP; i++) {
                long time = imageService.processImage(imageBytes);
                if (i > (WARMUP - 1) && time > 0) {
                    total += time;
                }
            }

            double averageMs = total / (double) ITERATIONS / 1000000;

            return BenchmarkResult.builder()
                    .type("HTTP")
                    .averageMs(averageMs)
                    .iterations(ITERATIONS)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/nats")
    public BenchmarkResult benchmarkNats(@RequestParam("image") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();

            long total = 0;

            for (int i = 0; i < ITERATIONS + WARMUP; i++) {
                long time = imageServiceForNats.processImage(imageBytes);
                if (i > (WARMUP - 1) && time > 0) {
                    total += time;
                }
            }

            double averageMs = total / (double) ITERATIONS / 1000000;

            return BenchmarkResult.builder()
                    .type("NATS")
                    .averageMs(averageMs)
                    .iterations(ITERATIONS)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
