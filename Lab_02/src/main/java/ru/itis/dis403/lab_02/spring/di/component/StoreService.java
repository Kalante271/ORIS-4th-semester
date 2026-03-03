package ru.itis.dis403.lab_02.spring.di.component;

import org.springframework.stereotype.Component;
import ru.itis.dis403.lab_02.spring.di.model.Basa;
import ru.itis.dis403.lab_02.spring.di.model.Fruit;
import ru.itis.dis403.lab_02.spring.di.model.FruitType;
import ru.itis.dis403.lab_02.spring.di.model.Store;

import java.util.List;

@Component
public class StoreService {
    private Basa basa = new Basa();

    public void add(String name) {
        basa.getStores().add(new Store(name));
    }

    public void addFruit(Store store, Fruit fruit, Integer count) {
        store.getFruits().put(fruit, count);
    }

    public List<Store> getAll() {
        return basa.getStores();
    }

    public Store findByName(String name) {
        return basa.getStores().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public long getCountFruitByStore(String name, FruitType type) {
        return basa.getStores().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow()
                .getFruits()
                .entrySet().stream().filter(f -> f.getKey().getType() == type)
                .count();
    }

}
