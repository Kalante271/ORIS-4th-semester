package ru.itis.dis403.lab_02.orm.util;

import ru.itis.dis403.lab_02.orm.annotation.Entity;

import java.io.File;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PathScan {

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(".", "/");
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + scannedPackage);
        }
        File scannedDir;
        try {
            scannedDir = new File(scannedUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }


    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                Class clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Entity.class)) {
                    classes.add(clazz);
                }
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}