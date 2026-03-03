package ru.itis.dis403.lab_01.di.model;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private String name;
    private Map<Fruit, Integer> fruits = new HashMap<>();

    public Store() {}

    public Store(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Fruit, Integer> getFruits() {
        return fruits;
    }

    public void setFruits(Map<Fruit, Integer> fruits) {
        this.fruits = fruits;
    }


    @Override
    public String toString() {
        return "Store{" + "name=" + name + ", fruits=" + fruits + '}';
    }
}
