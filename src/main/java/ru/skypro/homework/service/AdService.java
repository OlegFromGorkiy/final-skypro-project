package ru.skypro.homework.service;

import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;


public interface AdService {
    AdDTO createAd(CreateAds createAds, long id);

    @Transactional(readOnly = true)
    ResponseWrapperAds getAllAd();

    FullAds getFullAdDto(long id);

    void deleteAdDto(long id);

    AdDTO updateAdDto(long id, CreateAds createAds);

    ResponseWrapperAds getAllUserAd(long id);
}
