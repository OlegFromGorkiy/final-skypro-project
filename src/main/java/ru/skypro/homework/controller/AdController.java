package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAllAd() {
        return ResponseEntity.ok(adService.getAllAd());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestBody CreateAds createAds, @PathVariable Long id) {
        return ResponseEntity.ok(adService.createAd(createAds, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(
            @PathVariable Long id) {
        return ResponseEntity.ok(adService.getFullAdDto(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(
            @PathVariable Long id) {
        adService.deleteAdDto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAd(
            @PathVariable Long id,
            @RequestBody CreateAds createAdsDto) {
        return ResponseEntity.ok(adService.updateAdDto(id, createAdsDto));
    }
}