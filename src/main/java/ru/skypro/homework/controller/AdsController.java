package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAd() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @PostMapping
    public ResponseEntity<Ads> createNewAd(@RequestBody CreateAds properties,
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

        Ads response = new Ads();
        response.setTitle(properties.getTitle());
        response.setPrise(properties.getPrise());
        response.setTitle(properties.getTitle());
        response.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAdFullInfo(@PathVariable(name = "id") int id) {
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new FullAds());
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
    public ResponseEntity<Ads> editAd(@PathVariable(name = "id") int id,
                                      @RequestBody CreateAds properties) {
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Ads response = new Ads();
        response.setTitle(properties.getTitle());
        response.setPrise(properties.getPrise());
        response.setTitle(properties.getTitle());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getUsersAds() {
        if (false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(new ResponseWrapperAds());
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
