package ru.itis.dis403.lab_03.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
public class Admin extends Person{

    private String email;
}
