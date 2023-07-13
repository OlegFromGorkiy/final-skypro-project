package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, PasswordEncoder encoder, UserMapper mapper) {
        this.userService = userService;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> changePassword(@RequestBody NewPassword newPassword,
                                                      Authentication authentication) {
//        if (!authentication.isAuthenticated()) {       //как я понял это условие неявно гдето прописано
//            logger.info("unauthenticated user try change password");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

        User user = getAuthenticatedUser(authentication);

        if (user == null) {
            logger.info("unknown user try change password");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!(encoder.matches(newPassword.getCurrentPassword(), user.getPassword()))) {
            logger.info("user try change password with wrong password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok(newPassword);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getInfo(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        logger.info(String.format("User %s was updated", user.getEmail()));
        return ResponseEntity.ok(mapper.toUserDTO(user));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> changeInfo(@RequestBody UpdateUser update, Authentication authentication) {
        userService.updateUser(getAuthenticatedUser(authentication), update);
        return ResponseEntity.ok(update);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> changeImage(@RequestBody String image) {
        if (false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return userService.getByEmail(authentication.getName());
    }
}
