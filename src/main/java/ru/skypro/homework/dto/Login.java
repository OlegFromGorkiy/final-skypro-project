package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO класс для авторизации пользователя
 */
@Data
public class Login {
    private String password;
    private String username;

}
