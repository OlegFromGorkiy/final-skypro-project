package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;


    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Override
    public List<Ad> getAll() {
        return adRepository.findAll();
    }

    @Override
    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }

    @Override
    public Ad createAd(UpdateAd newAd, User author, String imagePath) {
        Ad ad = new Ad();
        ad.setAuthor(author);
        ad.setPrice(newAd.getPrice());
        ad.setTitle(newAd.getTitle());
        ad.setImage(imagePath);
        ad.setDescription(newAd.getDescription());
        saveAd(ad);
        return ad;
    }

    @Override
    public Ad getById(int id) {
        Optional<Ad> optional = adRepository.findAdById(id);
        return optional.isEmpty() ? null : optional.get();
    }

    @Override
    public void deleteAd(Ad ad) {
        adRepository.delete(ad);
    }

    @Override
    public Ad updateAd(Ad ad, UpdateAd update) {
        ad.setPrice(update.getPrice());
        ad.setTitle(update.getTitle());
        ad.setDescription(update.getDescription());
        saveAd(ad);
        return ad;
    }

    @Override
    public List<Ad> getAllByAuthor(User author) {
        return adRepository.findAllByAuthorId(author.getId());
    }
}
