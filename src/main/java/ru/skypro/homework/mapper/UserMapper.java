package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

/**
 * Интерфейс для маппинга пользователей.
 * Объект для осуществления маппинга генерирует библиотека MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role", target = "role")
    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO user);

    @Mapping(source = "register.username", target = "email")
    User fromRegister(Register register);
}
