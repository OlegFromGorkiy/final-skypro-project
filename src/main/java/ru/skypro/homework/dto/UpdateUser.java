package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO класс для обновления данных пользователя
 */
@Data
public class UpdateUser {
    private String firstname;
    private String lastName;
    private String phone;
}
