package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@SpringBootTest
public class UserMapperTest {
    private static final User USER;

    //given
    static {
        USER = new User();
        USER.setId(1);
        USER.setEmail("email");
        USER.setFirstName("firstName");
        USER.setLastName("lastName");
        USER.setPhone("phone");
        USER.setImage("image");
    }

    @Autowired
    UserMapper mapper;

    @Test
    public void checkToUserDTO() {
        //when
        UserDTO result = mapper.toUserDTO(USER);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), USER.getId());
        Assertions.assertEquals(result.getEmail(), USER.getEmail());
        Assertions.assertEquals(result.getFirstName(), USER.getFirstName());
        Assertions.assertEquals(result.getLastName(), USER.getLastName());
        Assertions.assertEquals(result.getPhone(), USER.getPhone());
        Assertions.assertEquals(result.getImage(), USER.getImage());
    }

    @Test
    public void checkFromUserDTO(){
        //when
        User result = mapper.fromUserDTO(mapper.toUserDTO(USER));
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), USER.getId());
        Assertions.assertEquals(result.getEmail(), USER.getEmail());
        Assertions.assertEquals(result.getFirstName(), USER.getFirstName());
        Assertions.assertEquals(result.getLastName(), USER.getLastName());
        Assertions.assertEquals(result.getPhone(), USER.getPhone());
        Assertions.assertEquals(result.getImage(), USER.getImage());
    }
}
