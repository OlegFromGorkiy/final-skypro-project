package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {

    private final AdService adService;
    private final UserService userService;
    private final AdMapper mapper;

    public AdController(AdService adService, UserService userService, AdMapper mapper) {
        this.adService = adService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Ads> getAllAd() {
        Ads ads = mapper.toAds(adService.getAll());
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<AdDTO> createNewAd(@RequestBody UpdateAd newAd,
                                             @RequestBody String image, Authentication authentication) {
        //some code for image
        User author = userService.getByEmail(authentication.getName());
        AdDTO response = mapper.toAdDTO(adService.createAd(newAd, author, "imagePath"));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdFullInfo(@PathVariable(name = "id") int id,
                                                    Authentication authentication) {
        Ad ad = adService.getById(id);

        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(mapper.toExtendedAd(ad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable(name = "id") int id, Authentication authentication) {
        Ad ad = adService.getById(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        adService.deleteAd(ad);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> editAd(@PathVariable(name = "id") int id,
                                        @RequestBody UpdateAd update, Authentication authentication) {
        Ad ad = adService.getById(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(mapper.toAdDTO(adService.updateAd(ad, update)));
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getUsersAds(Authentication authentication) {
        List<Ad> ads = adService.getAllByAuthor(userService.getByEmail(authentication.getName()));
        return ResponseEntity.ok(mapper.toAds(ads));
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<String> editImageAd(@PathVariable(name = "id") int id,
                                              @RequestBody String image) {

        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(image);
    }
}
