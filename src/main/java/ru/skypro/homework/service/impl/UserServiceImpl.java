package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.FileService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String IMAGE_DIRECTORY;
    private final FileService fileService;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(@Value("${path.to.user.images}") String imageDirectory, FileService fileService,
                           UserMapper mapper, UserRepository userRepository, PasswordEncoder encoder) {
        IMAGE_DIRECTORY = imageDirectory;
        this.fileService = fileService;
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
        return optional.isEmpty() ? null : optional.get();// возможно стоит кинут ошибку
    }

    @Override
    public User getFromAuthentication(Authentication authentication) {
        return getByEmail(authentication.getName());
    }

    @Override
    public UserDTO getUserInfo(Authentication authentication) {
        User user = getFromAuthentication(authentication);
        UserDTO result = mapper.toUserDTO(getFromAuthentication(authentication));
        result.setImage("/image/user/" + user.getEmail());
        return result;
    }

    @Override
    public void updateInfo(UpdateUser update, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        user.setFirstName(update.getFirstname());
        user.setLastName(update.getLastName());
        user.setPhone(user.getPhone());
        saveUser(user);
    }

    @Override
    public void setImage(MultipartFile image, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        String oldName = user.getImage();
        String sourceName = image.getOriginalFilename();//"image.jpg";//
        String fileName = user.getId() + sourceName.substring(sourceName.lastIndexOf("."));
        Path path = Path.of(IMAGE_DIRECTORY).resolve(fileName);
        try {
            if (oldName != null){
                Files.deleteIfExists(Path.of(oldName));
            }
            fileService.saveFile(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);//какая нибудь своя ошибка желательна
        }
        user.setImage(path.toString());
        saveUser(user);
    }

    @Override
    public byte[] getImage(String email) {
        String filePath = getByEmail(email).getImage();
        try {
            return fileService.readFile(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MediaType getImageType(String email) {
        String filePath = getByEmail(email).getImage();
        String subtype = filePath.substring(filePath.lastIndexOf(".")).replace(".", "");
        return new MediaType("image", subtype);
    }
}
