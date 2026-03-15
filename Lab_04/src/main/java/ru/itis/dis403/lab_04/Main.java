package ru.itis.dis403.lab_04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.lab_04.config.Config;
import ru.itis.dis403.lab_04.model.Phone;
import ru.itis.dis403.lab_04.repository.PhoneRepository;
import ru.itis.dis403.lab_04.service.PhoneService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Phone phone = new Phone();
        phone.setNumber("123");
        //phone.setId(2l);

        //PhoneRepository repository = context.getBean(PhoneRepository.class);
        //repository.save(phone);
        PhoneService service = (PhoneService) context.getBean(PhoneService.class);
        service.save(phone);

        //List<Phone> phoneList = service.findAll();
        List<Phone> phoneList = service.getPhoneLike("1%");

        phoneList.forEach(p -> System.out.println(p.getNumber()));
    }
}
