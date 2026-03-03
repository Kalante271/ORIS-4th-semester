package ru.itis.dis403.lab_02.spring.di.model;

import java.util.ArrayList;
import java.util.List;

public class Basa {
    private List<Store> stores = new ArrayList<>();

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
