package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.User;

public interface UserService extends UserDetailsService {
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
     * @param user пользователь чей email используется для проверки
     * @return true если в базе уже есть пользователь с аналогичным email
     */
    boolean emailCheck(User user);

    /**
     * Поиск пользователя в базе по email
     *
     * @param email email искомого пользователя
     * @return null если пользователь с данным email отсутствует в базе
     */
    User getByEmail(String email);

    /**
     * Обновление данных пользователя
     *
     * @param user   пользователь чьи данные обновляются; не должен быть null
     * @param update данные для обновления
     * @return обновленный пользователь сохраненный в базе; никогда не будет null
     */
    User updateUser(User user, UpdateUser update);
}
