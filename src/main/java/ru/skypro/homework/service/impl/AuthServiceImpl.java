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
        */
        User user = userService.getByEmail(userName);
        if (user == null) return false;
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(Register register, Role role) {
    /*
    if (manager.userExists(registerReq.getUsername())) {
      return false;
    }
    manager.createUser(
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerReq.getPassword())
            .username(registerReq.getUsername())

            .roles(role.name())
            .build());
    return true;
    */
        User user = userMapper.fromRegister(register);
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));//because unprotected password in database is bad solution
        if (userService.emailCheck(user)) {
            return false;
        }
        userService.saveUser(user);
        return true;
    }


}
