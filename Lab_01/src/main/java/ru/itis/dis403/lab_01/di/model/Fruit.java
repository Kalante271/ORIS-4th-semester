package ru.itis.dis403.lab_01.di.model;

public class Fruit {
    private String name;
    private FruitType type;

    public Fruit() {
    }

    public Fruit(String name, FruitType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public FruitType getType() {
        return type;
    }
    public void setType(FruitType type) {
        this.type = type;
    }
}
