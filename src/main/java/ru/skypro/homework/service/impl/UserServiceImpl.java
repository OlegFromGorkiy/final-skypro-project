package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserMapper mapper, UserRepository userRepository, PasswordEncoder encoder) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void changePassword(NewPassword newPassword, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        if (!(encoder.matches(newPassword.getCurrentPassword(), user.getPassword()))) {
            throw new ForbiddenException(String.format("Wrong current password entered by %s",
                    user.getEmail()));
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        saveUser(user);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        return optional.isEmpty() ? null : optional.get();
    }

    @Override
    public User getFromAuthentication(Authentication authentication) {
        return getByEmail(authentication.getName());
    }

    @Override
    public UserDTO getUserInfo(Authentication authentication) {
        return mapper.toUserDTO(getFromAuthentication(authentication));
    }

    @Override
    public void updateInfo(UpdateUser update, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        user.setFirstName(update.getFirstname());
        user.setLastName(update.getLastName());
        user.setPhone(user.getPhone());
        saveUser(user);
    }
}
