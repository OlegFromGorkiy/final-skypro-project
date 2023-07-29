package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.Role;

/**
 * Сервис для авторизации и создания новых пользователей
 */
public interface AuthService {
    /**
     * Авторизация пользователя
     *
     * @param userName логин (email) пользователя
     * @param password пароль пользователя (введенный на сайте, без шифрования)
     * @return true если пароль соответствует паролю пользователя в БД
     */
    boolean login(String userName, String password);

    /**
     * Регистрация нового пользователя
     *
     * @param registerReq DTO объект для регистрации пользователя
     * @param role        роль пользователя
     * @return true при успешном создании нового пользоваетля
     */
    boolean register(Register registerReq, Role role);
}
