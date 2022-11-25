package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.CreateUserRequestDto;
import com.fiec.lpiiiback.models.dto.UserDto;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public void signUp(@RequestBody CreateUserRequestDto createUserRequestDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        userService.signUpUser(user.getId(), createUserRequestDto.getName(), createUserRequestDto.getPhoneNumber(), createUserRequestDto.getAge());

    }

    @PostMapping("/{userId}")
    public UserDto info(@PathVariable("userId") Integer userId){

        return UserDto.convertToUserDto(userService.getUserById(userId));


    }

}
