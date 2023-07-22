package ru.skypro.homework.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    private final UserService userService;
 //   private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> changePassword(@RequestBody NewPassword newPassword,
                                                      Authentication authentication) {
        userService.changePassword(newPassword, authentication);
        return ResponseEntity.ok(newPassword);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> changeInfo(@RequestBody UpdateUser update, Authentication authentication) {
        userService.updateInfo(update,authentication);
        return ResponseEntity.ok(update);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> changeImage(@RequestBody String image) {
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

}
