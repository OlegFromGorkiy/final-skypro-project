package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UpdateAd {
    private String description;
    private int price;
    private String title;
}
