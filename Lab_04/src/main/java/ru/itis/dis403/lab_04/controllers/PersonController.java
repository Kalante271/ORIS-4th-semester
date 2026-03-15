package ru.itis.dis403.lab_04.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dis403.lab_04.model.Person;
import ru.itis.dis403.lab_04.model.Phone;
import ru.itis.dis403.lab_04.service.PersonService;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getAllPersons(Model model) {
        List<Person> persons = personService.findAll();
        model.addAttribute("persons", persons);
        return "persons";
    }

    @GetMapping("/add")
    public String addPersonForm(Model model) {
        Person person = new Person();
        person.setPhone(new Phone());
        model.addAttribute("person", person);
        return "person_add";
    }

    @PostMapping("/add")
    public String savePerson(@ModelAttribute Person person) {
        personService.save(person);
        return "redirect:/persons";
    }
}
