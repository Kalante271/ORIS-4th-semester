package ru.itis.dis403.lab_02.orm.model;

import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Entity;
import ru.itis.dis403.lab_02.orm.annotation.Id;

@Entity
public class Country {

    @Id
    private Long id;

    @Column
    private String name;

    public Country() {
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
