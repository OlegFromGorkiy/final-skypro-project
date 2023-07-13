package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.UserService;

import javax.xml.bind.ValidationException;
import java.util.Collection;

import static ru.skypro.homework.dto.Role.USER;
import static ru.skypro.homework.security.SecurityUtils.getUserDetailsFromContext;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws ValidationException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }
        if (user.getRole() == null) {
            user.setRole(USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = getUserById(getUserDetailsFromContext().getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден!"));
    }

    @Override
    public void newPassword(String newPassword, String currentPassword) {
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Неверно указан текущий пароль!");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }

    @Override
    public User updateRole(long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }
}