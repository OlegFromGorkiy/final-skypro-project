package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO класс для обновления объявления
 */
@Data
public class UpdateAd {
    private String description;
    private int price;
    private String title;
}
