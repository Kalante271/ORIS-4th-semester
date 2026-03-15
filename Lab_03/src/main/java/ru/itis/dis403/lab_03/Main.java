package ru.itis.dis403.lab_03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import ru.itis.dis403.lab_03.model.Admin;
import ru.itis.dis403.lab_03.model.Client;
import ru.itis.dis403.lab_03.model.Phone;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_03");

        Admin admin = new Admin();
        admin.setId(10l);
        admin.setName("admin10");
        admin.setEmail("asd@awd");

        Client client = new Client();
        client.setId(11l);
        client.setName("client11");
        client.setAddress("address");

        Phone phone1 = new Phone();
        phone1.setId(1l);
        phone1.setNumber("111111");

        Phone phone2 = new Phone();
        phone2.setId(2l);
        phone2.setNumber("222222");

        client.getPhones().add(phone1);
        client.getPhones().add(phone2);

        admin.getPhones().add(phone1);


//        Person person = new Person();
//        person.setId(3l);
//        person.setName("person3");

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
//        em.persist(phone1);
//        em.persist(phone2);
        em.persist(admin);
        em.persist(client);
//        em.persist(person);
        et.commit();

        em.close();
        emf.close();
    }
}
