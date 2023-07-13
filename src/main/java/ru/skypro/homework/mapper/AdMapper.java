package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {
    @Mapping(source = "ad.id", target = "pk")
    @Mapping(source = "ad.author.id", target = "author")
    AdDTO toAds(Ad ad);

    @Mapping(source = "ad.id", target = "pk")
    @Mapping(source = "ad.author.firstName", target = "authorFirstName")
    @Mapping(source = "ad.author.lastName", target = "authorLastName")
    @Mapping(source = "ad.author.email", target = "email")
    @Mapping(source = "ad.author.phone", target = "phone")
    ExtendedAd toFullAds(Ad ad);

    default Ads toAds(List<Ad> adList) {
        Ads rwa = new Ads();
        rwa.setCount(adList.size());
        rwa.setResult(adList.stream()
                .map(this::toAds)
                .collect(Collectors.toList()));
        return rwa;
    }
//закоментил потому что проще написать метод в сервисе
//    Ad fromUpdateAds(UpdateAd updateAd);
}
