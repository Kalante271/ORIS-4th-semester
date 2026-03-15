package ru.itis.dis403.lab_02.orm;

import ru.itis.dis403.lab_02.orm.model.City;
import ru.itis.dis403.lab_02.orm.model.Country;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = new EntityManagerFactory();
        EntityManager em = emf.getEntityManager();

        emf.validate();
        System.out.println();
        emf.printTablesAndEntities();
        System.out.println();
        test(em);
    }

    private static void test(EntityManager entityManager) {
        Country country = new Country();
        country.setName("Russia");
        entityManager.save(country);

        City city1 = new City();
        city1.setName("Kazan");
        city1.setCountry(country);
        entityManager.save(city1);

        City city2 = new City();
        city2.setName("Moscow");
        city2.setCountry(country);
        entityManager.save(city2);

        City foundCity = entityManager.find(City.class, city1.getId());
        System.out.println(foundCity.getName());

        List<City> cities = entityManager.findAll(City.class);
        System.out.println(cities);

        city1.setName("Kazan Updated");
        entityManager.save(city1);

        entityManager.remove(city2);
    }

}
