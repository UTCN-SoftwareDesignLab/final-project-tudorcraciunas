package com.example.hardwarestore.user.dto;


import com.example.hardwarestore.user.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Data
@SuperBuilder
@RequiredArgsConstructor
public class UserDTO {

    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private String phone = "";

    @Builder.Default
    private Set<Role> role =  new HashSet<>();

}