package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ad;

import java.util.ArrayList;
import java.util.Collection;
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
    FullAds toFullAds(Ad ad);

    default ResponseWrapperAds toResponseWrapper(List<Ad> adList) {
        ResponseWrapperAds rwa = new ResponseWrapperAds();
        rwa.setCount(adList.size());
        rwa.setResult(adList.stream()
                .map(this::toAds)
                .collect(Collectors.toList()));
        return rwa;
    }

    Ad fromCreateAds(CreateAds createAds);

    default AdDTO mapAdToAdDto(Ad ad) {
        AdDTO adDto = new AdDTO();
        adDto.setPk(ad.getId());
        adDto.setAuthor(ad.getAuthor().getId());
        adDto.setPrice(ad.getPrice());
        adDto.setImage(ad.getImage());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    default List<AdDTO> mapAdListToAdDtoList(Collection<Ad> adCollection) {
        List<AdDTO> dtoList = new ArrayList<>(adCollection.size());
        for (Ad ad : adCollection) {
            dtoList.add(mapAdToAdDto(ad));
        }
        return dtoList;
    }

    default Ad mapCreatedAdsDtoToAd(CreateAds createAds) {
        Ad ad = new Ad();
        ad.setTitle(createAds.getTitle());
        ad.setDescription(createAds.getDescription());
        ad.setPrice(createAds.getPrice());
        return ad;
    }

    default FullAds mapAdToFullAds(Ad ad) {
        FullAds fullAds = new FullAds();
        fullAds.setPk(ad.getId());
        fullAds.setAuthorFirstName(ad.getAuthor().getFirstName());
        fullAds.setAuthorLastName(ad.getAuthor().getLastName());
        fullAds.setEmail(ad.getAuthor().getEmail());
        fullAds.setPhone(ad.getAuthor().getPhone());
        fullAds.setTitle(ad.getTitle());
        fullAds.setDescription(ad.getDescription());
        fullAds.setImage(ad.getImage());
        fullAds.setPrice(ad.getPrice());
        return fullAds;
    }
}