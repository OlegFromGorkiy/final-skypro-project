package ru.skypro.homework.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserService userService, UserMapper userMapper) {
        this.encoder = passwordEncoder;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
/*
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
*/

        User user = userService.getByEmail(userName);
        if (user == null) return false;
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(Register register, Role role) {
/*
    if (manager.userExists(register.getUsername())) {
      return false;
    }
    manager.createUser(
        User.builder()
            .password(this.encoder::encode)
            .password(register.getPassword())
            .username(register.getUsername())

            .roles(role.name())
            .build());
    return true;
*/

        if (userService.emailCheck(register.getUsername())) {
            return false;
        }
        User user = userMapper.fromRegister(register);
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));//because unprotected password in database is bad solution
        userService.saveUser(user);
        return true;
    }

}
