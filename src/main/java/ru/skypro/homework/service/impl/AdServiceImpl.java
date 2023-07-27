package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import ru.skypro.homework.service.FileService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private final String IMAGE_DIRECTORY;
    private final FileService fileService;
    private final AdRepository adRepository;
    private final AdMapper mapper;
    private final UserService userService;


    public AdServiceImpl(@Value("${path.to.ad.images}") String imageDirectory, FileService fileService,
                         AdRepository adRepository, AdMapper mapper, UserService userService) {
        IMAGE_DIRECTORY = imageDirectory;
        this.fileService = fileService;
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
    public AdDTO createAd(UpdateAd newAd, Authentication authentication, MultipartFile image) {
        Ad ad = new Ad();
        ad.setAuthor(userService.getFromAuthentication(authentication));
        ad.setPrice(newAd.getPrice());
        ad.setTitle(newAd.getTitle());
        ad.setDescription(newAd.getDescription());
        saveAd(ad);
        setImage(image, ad);
        return mapper.toAdDTO(saveAd(ad));
    }

    @Override
    public ExtendedAd getFullInfo(int id) {
        return mapper.toExtendedAd(getById(id));
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
        return mapper.toAdDTO(ad);
    }

    @Override
    public Ads getAllByAuthor(Authentication authentication) {
        User author = userService.getFromAuthentication(authentication);
        List<Ad> ads = adRepository.findAllByAuthor(author);
        return mapper.toAds(ads);
    }

    @Override
    public void updateImage(int id, MultipartFile image, Authentication authentication) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
        setImage(image, ad);
    }

    private void authorCheck(Ad ad, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getAuthor())) {
            throw new ForbiddenException(String.format("%s can't edit or delete this Ad", user.getEmail()));
        }
    }

    private void setImage(MultipartFile image, Ad ad) {
        String oldName = ad.getImage();
        String sourceName = image.getOriginalFilename();
        String fileName = ad.getId() + sourceName.substring(sourceName.lastIndexOf("."));
        Path path = Path.of(IMAGE_DIRECTORY).resolve(fileName);
        try {
            if (oldName != null) {
                Files.deleteIfExists(Path.of(oldName));
            }
            fileService.saveFile(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ad.setImage(path.toString());
        saveAd(ad);
    }

    @Override
    public byte[] getImage(int id) {
        String filePath = getById(id).getImage();
        try {
            return fileService.readFile(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MediaType getImageType(int id) {
        String filePath = getById(id).getImage();
        String subtype = filePath.substring(filePath.lastIndexOf(".")).replace(".", "");
        return new MediaType("image", subtype);
    }
}
