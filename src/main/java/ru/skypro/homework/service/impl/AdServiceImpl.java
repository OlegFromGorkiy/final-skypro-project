package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;



    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

}
