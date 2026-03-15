package ru.itis.dis403.lab_03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import ru.itis.dis403.lab_03.model.Person;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_03");

        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();

        et.begin();
        //Извлечем всех Person
        List<Person> persons = em.createQuery("select p from Person p where p.id = 11").getResultList();

        persons.forEach(System.out::println);

        if (persons != null && !persons.isEmpty()) {
            persons.get(0).getPhones().forEach(System.out::println);
        }

        et.commit();

        em.close();
        emf.close();
    }
}
