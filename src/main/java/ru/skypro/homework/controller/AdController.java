package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;

    }

    @GetMapping
    public ResponseEntity<Ads> getAllAd() {
        return ResponseEntity.ok(adService.getAll());
    }

    @PostMapping
    public ResponseEntity<AdDTO> createNewAd(@RequestPart(name = "properties") UpdateAd object,
                                             @RequestPart(name = "image") MultipartFile image, Authentication authentication) {
        AdDTO result = adService.createAd(object, authentication, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdFullInfo(@PathVariable(name = "id") int id,
                                                    Authentication authentication) {
        ExtendedAd result = adService.getFullInfo(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable(name = "id") int id, Authentication authentication) {
        adService.deleteAd(id, authentication);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> editAd(@PathVariable(name = "id") int id,
                                        @RequestBody UpdateAd update, Authentication authentication) {
        return ResponseEntity.ok(adService.updateAd(id, update, authentication));
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getUsersAds(Authentication authentication) {
        return ResponseEntity.ok(adService.getAllByAuthor(authentication));
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<byte[]> editImageAd(@PathVariable(name = "id") int id,
                                              @RequestBody MultipartFile image, Authentication authentication) {
        try {
            adService.updateImage(id, image, authentication);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
