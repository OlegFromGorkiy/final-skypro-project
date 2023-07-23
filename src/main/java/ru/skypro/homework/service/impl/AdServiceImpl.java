package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.UpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundElement;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper mapper;
    private final UserService userService;


    public AdServiceImpl(AdRepository adRepository, AdMapper mapper, UserService userService) {
        this.adRepository = adRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Ads getAll() {
        return mapper.toAds(adRepository.findAll());
    }

    @Override
    public Ad saveAd(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public AdDTO createAd(UpdateAd newAd, Authentication authentication, String image) {
        Ad ad = new Ad();
        ad.setAuthor(userService.getFromAuthentication(authentication));
        ad.setPrice(newAd.getPrice());
        ad.setTitle(newAd.getTitle());
        ad.setDescription(newAd.getDescription());

        ad.setImage(image);//need valid code for image!
        saveAd(ad);
        return mapper.toAdDTO(ad);
    }

    @Override
    public ExtendedAd getFullInfo(int id) {
        Ad ad = getById(id);
        return mapper.toExtendedAd(ad);
    }

    @Override
    public Ad getById(int id) {
        Optional<Ad> optional = adRepository.findAdById(id);
        if (optional.isEmpty()) {
            throw new NotFoundElement(id, Ad.class);
        }
        return optional.get();
    }

    @Override
    public void deleteAd(int id, Authentication authentication) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
        adRepository.delete(ad);
    }

    @Override
    public AdDTO updateAd(int id, UpdateAd update, Authentication authentication) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
        ad.setPrice(update.getPrice());
        ad.setTitle(update.getTitle());
        ad.setDescription(update.getDescription());
        return mapper.toAdDTO(saveAd(ad));
    }

    @Override
    public Ads getAllByAuthor(Authentication authentication) {
        int authorId = userService.getFromAuthentication(authentication).getId();
        List<Ad> ads = adRepository.findAllByAuthorId(authorId);
        return mapper.toAds(ads);
    }

    private void authorCheck(Ad ad, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getAuthor())) {
            throw new ForbiddenException(String.format("%s can't edit or delete this Ad", user.getEmail()));
        }
    }
}
