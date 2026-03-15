package ru.itis.dis403.lab_04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.dis403.lab_04.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
