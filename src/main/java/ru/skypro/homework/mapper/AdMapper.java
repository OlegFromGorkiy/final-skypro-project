package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    @Mapping(source = "ad.id", target = "pk")
    @Mapping(source = "ad.author.id", target = "author")
    Ads adToAds(Ad ad);

    @Mapping(source = "ad.id", target = "pk")
    @Mapping(source = "ad.author.firstName", target = "authorFirstName")
    @Mapping(source = "ad.author.lastName", target = "authorLastName")
    @Mapping(source = "ad.author.email", target = "email")
    @Mapping(source = "ad.author.phone", target = "phone")
    FullAds adToFullAds(Ad ad);

    default ResponseWrapperAds adToResponseWrapper(List<Ad> adList) {
        ResponseWrapperAds rwa = new ResponseWrapperAds();
        rwa.setCount(adList.size());
        rwa.setResult(adList.stream()
                .map(this::adToAds)
                .collect(Collectors.toList()));
        return rwa;
    }

    Ad adFromCreateAds(CreateAds createAds);
}
