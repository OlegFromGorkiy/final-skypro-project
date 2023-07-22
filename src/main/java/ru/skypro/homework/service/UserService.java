package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

public interface UserService {
    /**
     * Сохранение пользователя в базу
     *
     * @param user сохраняемый пользователь; не должен быть null
     * @return сохраненный пользователь; никогда не будет null
     */
    User saveUser(User user);

    /**
     * Поиск в базе пользователей с аналогичным email
     *
     * @param email email используемый для проверки
     * @return true если в базе уже есть пользователь с аналогичным email
     */
    boolean emailCheck(String email);

    /**
     * Изменение пароля пользователя
     *
     * @param newPassword    DTO с данными паролей
     * @param authentication данные аутентификации пользователя
     * @throws ru.skypro.homework.exception.ForbiddenException если текущий пароль в DTO  не совпадает с сохраненным в базе
     */
    void changePassword(NewPassword newPassword, Authentication authentication);

    /**
     * Поиск пользователя в базе по email
     *
     * @param email email искомого пользователя
     * @return null если пользователь с данным email отсутствует в базе
     */
    User getByEmail(String email);

    /**
     * Пользователь из данных аутентификации
     *
     * @param authentication данные аутентификации пользователя
     * @return объект пользователя из базы
     */
    User getFromAuthentication(Authentication authentication);

    /**
     * Информация о опльзователе в виде DTO
     * @param authentication данные аутентификации пользователя
     * @return DTO с данными пользователя
     */
    UserDTO getUserInfo(Authentication authentication);

    /**
     * Обновление данных пользователя
     * @param update новые данные пользователя в виде DTO
     * @param authentication данные аутентификации пользователя
     */
    void updateInfo(UpdateUser update, Authentication authentication);
}
