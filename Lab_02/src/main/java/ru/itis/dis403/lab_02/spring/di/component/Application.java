package ru.itis.dis403.lab_02.spring.di.component;

import org.springframework.stereotype.Component;
import ru.itis.dis403.lab_02.spring.di.model.Fruit;
import ru.itis.dis403.lab_02.spring.di.model.FruitType;
import ru.itis.dis403.lab_02.spring.di.model.Store;

@Component
public class Application {

    private StoreService service;

    public Application(StoreService service) {
        this.service = service;
    }

    public void run() {

        service.add("1");
        service.add("2");

        Store store1 = service.findByName("1");
        service.addFruit(store1, new Fruit("Яблоко", FruitType.APPLE), 1000);
        service.addFruit(store1, new Fruit("Бананы", FruitType.BANANA), 2000);

        Store store2 = service.findByName("2");
        service.addFruit(store2, new Fruit("Яблоко", FruitType.APPLE), 3000);
        service.addFruit(store2, new Fruit("Апельсины", FruitType.ORANGE), 2000);

        service.getAll().forEach(System.out::println);
    }
}
