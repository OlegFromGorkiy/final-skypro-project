package ru.skypro.homework.service;

import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ad;

import java.util.Collection;

public interface AdService {
    Ad createAd(Ad ad);

    @Transactional(readOnly = true)
    Ad getAdById(long id);

    @Transactional(readOnly = true)
    Collection<Ad> getAllAd();
}
