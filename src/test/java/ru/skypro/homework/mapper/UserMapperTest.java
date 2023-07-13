package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.Role;
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

    @Test
    public void CheckFromRegister(){
        //given
        Register register = new Register();
        register.setUsername("register@mail.com");
        register.setPassword("password");
        register.setFirstName("firstName");
        register.setLastName("lastName");
        register.setPhone("+71234567890");
        register.setRole(Role.ADMIN);
        //when
        User result = mapper.fromRegister(register);
        //then
        Assertions.assertNotNull(result);

        Assertions.assertEquals(result.getEmail(), register.getUsername());
        Assertions.assertEquals(result.getPassword(), register.getPassword());
        Assertions.assertEquals(result.getFirstName(), register.getFirstName());
        Assertions.assertEquals(result.getLastName(), register.getLastName());
        Assertions.assertEquals(result.getPhone(), register.getPhone());
        Assertions.assertEquals(result.getRole(), register.getRole());
    }
}
