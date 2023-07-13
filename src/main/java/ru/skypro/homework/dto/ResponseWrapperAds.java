package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private int count;
    private List<AdDTO> result;
    public ResponseWrapperAds(List<AdDTO> adDTOList) {
    }
    public ResponseWrapperAds() {
    }

}
