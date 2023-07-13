package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundExeption;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserService userService;
    private final AdMapper adMapper;

    /**
     * Метод ищет и возвращает список всех объявлений
     */
    @Override
    public ResponseWrapperAds getAllAd() {
        List<AdDTO> adDTOAll = adMapper.mapAdListToAdDtoList(adRepository.findAll());
        return new ResponseWrapperAds(adDTOAll);
    }

    /**
     * Метод создает объявление
     */
    @Override
    public AdDTO createAd(CreateAds createAds, long id) {
        Ad newAd = adMapper.mapCreatedAdsDtoToAd(createAds);
        newAd.setAuthor(userService.getUserById(id));
        adRepository.save(newAd);
        return adMapper.mapAdToAdDto(newAd);
    }

    /**
     * Метод ищет и возвращает объявление по id
     */
    @Override
    public FullAds getFullAdDto(long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundExeption("Объявление не найдено!"));
        return adMapper.mapAdToFullAds(ad);
    }

    /**
     * Метод удаляет объявление по id
     */
    @Override
    public void deleteAdDto(long id) {
        adRepository.deleteById(id);
    }

    /**
     * Метод редактирует объявление по id
     */
    @Override
    public AdDTO updateAdDto(long id, CreateAds createAds) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundExeption("Объявление не найдено!"));
        ad.setDescription(createAds.getDescription());
        ad.setPrice(createAds.getPrice());
        ad.setTitle(createAds.getTitle());
        return adMapper.mapAdToAdDto(adRepository.save(ad));
    }

    /**
     * Метод ищет и возвращает список всех объявлений авторизированного пользователя
     */
    @Override
    public ResponseWrapperAds getAllUserAd(long id) {
        User user = userService.getUserById(id);
        Collection<Ad> allAds = adRepository.findAll();
        Collection<Ad> userAds = new ArrayList<>();
        for (Ad ad : allAds) {
            if (ad.getAuthor().equals(user)) {
                userAds.add(ad);
            }
        }
        return new ResponseWrapperAds(adMapper.mapAdListToAdDtoList(userAds));
    }
}