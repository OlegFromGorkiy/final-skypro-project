package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundExeption;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

import static ru.skypro.homework.security.SecurityUtils.*;

@Transactional
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final UserService userService;

    private final AdRepository adRepository;

    @Override
    public Ad createAd(Ad ad) {

        User user = userService.getUserById(getUserIdFromContext());

        ad.setAuthor(user);

        return adRepository.save(ad);
    }

    @Transactional(readOnly = true)
    @Override
    public Ad getAdById(long id) {

        return adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundExeption("Объявление с id " + id + " не найдено!"));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Ad> getAllAd() {
        return adRepository.findAll();
    }
}