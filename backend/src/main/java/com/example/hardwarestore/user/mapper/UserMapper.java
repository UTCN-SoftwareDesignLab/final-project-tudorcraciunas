package com.example.hardwarestore.user.mapper;

import com.example.hardwarestore.user.dto.*;
import com.example.hardwarestore.user.dto.UserListDTO;
import com.example.hardwarestore.user.dto.UserMinimalDTO;
import com.example.hardwarestore.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }

    User fromDtoUserMinimal(UserMinimalDTO userDTO);

    //User fromDtoUserList(UserListDTO userListDTO);

    User fromUserDTO(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "role", source = "roles")

    })
    UserDTO toUserDTO(User user);
}
