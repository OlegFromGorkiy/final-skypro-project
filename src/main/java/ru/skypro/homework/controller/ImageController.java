package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

@RestController
@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    private final UserService userService;
    private final AdService adService;

    public ImageController(UserService userService, AdService adService) {
        this.userService = userService;
        this.adService = adService;
    }

    @GetMapping(value = "user/{email}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable(name = "email") String email) {
        return ResponseEntity
                .ok()
                .contentType(userService.getImageType(email))// .contentType(MediaType.IMAGE_JPEG) //
                .body(userService.getImage(email));
    }
   //   @GetMapping(value = "ad/{id}")
   //   public byte[] getAdImage(@PathVariable(name = "id") int id) {
       //       return adService.getImage(id);
       //   }


    @GetMapping(value = "ad/{id}")
    public ResponseEntity<byte[]> getAdImage(@PathVariable(name = "id") int id) {
        return ResponseEntity
                .ok()
                .contentType(adService.getImageType(id))
                .body(adService.getImage(id));
    }
}
