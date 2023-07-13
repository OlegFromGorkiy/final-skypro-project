package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdService adService;

    public AdsController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<Ads> getAllAd() {
        return ResponseEntity.ok(new Ads());
    }

    @PostMapping
    public ResponseEntity<AdDTO> createNewAd(@RequestBody UpdateAd properties,
                                             @RequestBody String image) {
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AdDTO response = new AdDTO();
        response.setTitle(properties.getTitle());
        response.setPrice(properties.getPrice());
        response.setTitle(properties.getTitle());
        response.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdFullInfo(@PathVariable(name = "id") int id) {
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new ExtendedAd());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable(name = "id") int id) {
        if (false) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> editAd(@PathVariable(name = "id") int id,
                                        @RequestBody UpdateAd properties) {
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AdDTO response = new AdDTO();
        response.setTitle(properties.getTitle());
        response.setPrice(properties.getPrice());
        response.setTitle(properties.getTitle());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getUsersAds() {
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(new Ads());
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
