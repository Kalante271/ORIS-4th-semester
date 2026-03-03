package ru.itis.dis403.lab_02.spring.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Loader {
    public static String load(String fileName) throws IOException {
        InputStream is = Loader.class.getClassLoader().getResourceAsStream("static/" + fileName);

        if (is == null) {
            throw new RuntimeException("File not found: " + fileName);
        }

        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
