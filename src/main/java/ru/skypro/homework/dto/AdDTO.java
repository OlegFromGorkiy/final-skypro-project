package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
<<<<<<<< HEAD:src/main/java/ru/skypro/homework/dto/AdDTO.java
public class AdDTO {
    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;
========
public class Ads {
    private int count;
    private List<AdDTO> result;
>>>>>>>> 6e1a1a4 (renamed DTO):src/main/java/ru/skypro/homework/dto/Ads.java
}
