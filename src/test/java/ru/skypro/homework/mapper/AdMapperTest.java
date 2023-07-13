package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class AdMapperTest {
    private static final User USER;
    private static final Ad AD;

    //given
    static {
        USER = new User();
        USER.setId(1);
        USER.setEmail("email");
        USER.setPhone("phoneNumber");
        USER.setFirstName("firstName");
        USER.setLastName("lastName");

        AD = new Ad();
        AD.setAuthor(USER);
        AD.setImage("image");
        AD.setId(1);
        AD.setPrice(100);
        AD.setTitle("title");
        AD.setDescription("description");
    }

    @Autowired
    private AdMapper mapper;

    @Test
    public void checkToADS() {
        //when
        AdDTO ads = mapper.toAds(AD);
        //then
        Assertions.assertNotNull(ads);
        Assertions.assertEquals(ads.getAuthor(), AD.getAuthor().getId());
        Assertions.assertEquals(ads.getImage(), AD.getImage());
        Assertions.assertEquals(ads.getPk(), AD.getId());
        Assertions.assertEquals(ads.getPrice(), AD.getPrice());
        Assertions.assertEquals(ads.getTitle(), AD.getTitle());
    }

    @Test
    public void checkToFullAds() {
        //when
        ExtendedAd ads = mapper.toFullAds(AD);
        //then
        Assertions.assertNotNull(ads);
        Assertions.assertEquals(ads.getPk(), AD.getId());
        Assertions.assertEquals(ads.getAuthorFirstName(), AD.getAuthor().getFirstName());
        Assertions.assertEquals(ads.getAuthorLastName(), AD.getAuthor().getLastName());
        Assertions.assertEquals(ads.getDescription(), AD.getDescription());
        Assertions.assertEquals(ads.getEmail(), AD.getAuthor().getEmail());
        Assertions.assertEquals(ads.getImage(), AD.getImage());
        Assertions.assertEquals(ads.getPhone(), AD.getAuthor().getPhone());
        Assertions.assertEquals(ads.getPrice(), AD.getPrice());
        Assertions.assertEquals(ads.getTitle(), AD.getTitle());
    }

    @Test
    public void checkToResponseWrapper() {
        //given
        List<Ad> adList = new LinkedList<>();
        adList.add(AD);
        //when
        Ads responseWrapperAds = mapper.toAds(adList);
        //then
        Assertions.assertNotNull(responseWrapperAds);
        Assertions.assertEquals(responseWrapperAds.getCount(), adList.size());
        Assertions.assertEquals(responseWrapperAds.getResult(),
                adList.stream()
                        .map(e -> mapper.toAds(e))
                        .collect(Collectors.toList()));
    }

//    @Test
//    public void checkFromCreateAds(){
//        //given
//        UpdateAd newAd = new UpdateAd();
//        newAd.setDescription("description");
//        newAd.setPrice(100);
//        newAd.setTitle("Title");
//        //when
//        Ad ads = mapper.fromCreateAds(newAd);
//        //then
//        Assertions.assertNotNull(ads);
//        Assertions.assertEquals(ads.getDescription(), newAd.getDescription());
//        Assertions.assertEquals(ads.getPrice(), newAd.getPrice());
//        Assertions.assertEquals(ads.getTitle(), newAd.getTitle());
//
//    }
}
