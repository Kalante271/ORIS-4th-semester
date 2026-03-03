package ru.itis.dis403.lab_02.context.components;

import ru.itis.dis403.lab_02.context.model.ImportProduct;
import ru.itis.dis403.lab_02.context.model.Market;
import ru.itis.dis403.lab_02.context.model.Order;

import java.util.NoSuchElementException;


public class MarketService {
    private Market market;

    public MarketService(Market market) {
        this.market = market;
    }

    public void doOrder(Order order) {
        Integer count = market.getProducts().get(order.getProduct());
        if (count == null || count < order.getCount()) {
            throw new NoSuchElementException();
        }
        market.getOrders().add(order);
        market.getProducts().put(order.getProduct(), count -  order.getCount());
    }

    public void doImport(ImportProduct importProduct) {
        Integer count = market.getProducts().get(importProduct.getProduct());
        if (count == null) {
            count = 0;
        }
        market.getImportProducts().add(importProduct);
        market.getProducts().put(importProduct.getProduct(), count + importProduct.getCount());
    }

    public void printProducts() {
        market.getProducts()
                .entrySet()
                .forEach(entry -> {System.out.println(entry.getKey() + ": " + entry.getValue());});
    }
}
