package com.example.hardwarestore.user;

import com.example.hardwarestore.security.AuthService;
import com.example.hardwarestore.security.dto.SignupRequest;
import com.example.hardwarestore.user.dto.UserDTO;
import com.example.hardwarestore.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.hardwarestore.UrlMapping.ENTITY;
import static com.example.hardwarestore.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public void create(@RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
    }

    @PatchMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.edit(id, userDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

}
