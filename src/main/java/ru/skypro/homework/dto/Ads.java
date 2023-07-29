package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO класс спсика объявлений
 */
@Data
public class Ads {
    private int count;
    private List<AdDTO> results;
}
