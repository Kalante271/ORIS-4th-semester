package ru.itis.dis403.lab_03.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.InheritanceType.*;

@Getter@Setter
//@MappedSuperclass
@Entity
@Inheritance(strategy = JOINED) //для каждой сущности своя таблица, только уникальные поля сущности, пк родителя = вк на наследника = пк наследника
//@Inheritance(strategy = SINGLE_TABLE) //одна таблица для всех сущностей
//@Inheritance(strategy = TABLE_PER_CLASS) //для каждой сущности своя таблица, у сущности все поля родителя
public class Person {

    @Id
    protected Long id;

    protected String name;

    @ManyToOne()
    protected Phone phone;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    protected Set<Phone> phones = new HashSet<>();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
