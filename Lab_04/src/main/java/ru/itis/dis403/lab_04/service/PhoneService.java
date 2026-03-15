package ru.itis.dis403.lab_04.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.itis.dis403.lab_04.model.Phone;
import ru.itis.dis403.lab_04.repository.PhoneRepository;

import java.util.List;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public List<Phone> getPhoneLike(String num) {
        return phoneRepository.findByNumberLike(num);
    }
}
