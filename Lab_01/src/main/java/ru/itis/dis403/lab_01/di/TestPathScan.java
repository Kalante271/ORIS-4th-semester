package ru.itis.dis403.lab_01.di;

import ru.itis.dis403.lab_01.di.config.Context;

public class TestPathScan {
    public static void main(String[] args) {

//        List<Class<?>> classes = PathScan.find("ru.itis.dis403.lab_01");
//        classes.forEach(System.out::println);

        new Context();
    }
}