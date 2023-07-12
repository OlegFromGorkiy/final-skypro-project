package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User fromUserDTO(UserDTO user);

    @Mapping(source = "req.username", target = "email")
    User fromRegisterReq(RegisterReq req);
}
