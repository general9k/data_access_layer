package ru.rodionov.lab_5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.lab_5.dto.UserDTO;
import ru.rodionov.lab_5.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO toDto(User user);
}
