package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.entity.Ad;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdMapper {
    //@Mapping(source = "ad.id", target = "pk")
    //@Mapping(source = "ad.author.id", target = "author")
    default AdDTO toAdDTO(Ad ad) {
        AdDTO result = new AdDTO();
        result.setAuthor(ad.getAuthor().getId());
        result.setImage("/image/ad/" + ad.getId());
        result.setPk(ad.getId());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }

    //@Mapping(source = "ad.id", target = "pk")
    //@Mapping(source = "ad.author.firstName", target = "authorFirstName")
    //@Mapping(source = "ad.author.lastName", target = "authorLastName")
    //@Mapping(source = "ad.author.email", target = "email")
    //@Mapping(source = "ad.author.phone", target = "phone")
    default ExtendedAd toExtendedAd(Ad ad) {
        ExtendedAd result = new ExtendedAd();
        result.setPk(ad.getId());
        result.setAuthorFirstName(ad.getAuthor().getFirstName());
        result.setAuthorLastName(ad.getAuthor().getLastName());
        result.setDescription(ad.getDescription());
        result.setEmail(ad.getAuthor().getEmail());
        result.setImage("/image/ad/" + ad.getId());
        result.setPhone(ad.getAuthor().getPhone());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }

    default Ads toAds(List<Ad> adList) {
        Ads ads = new Ads();
        ads.setCount(adList.size());
        ads.setResult(adList.stream()
                .map(this::toAdDTO)
                .collect(Collectors.toList()));
        return ads;
    }
//    Ad fromUpdateAds(UpdateAd updateAd);
}
