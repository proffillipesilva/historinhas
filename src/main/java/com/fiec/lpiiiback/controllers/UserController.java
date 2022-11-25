package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.CreateUserRequestDto;
import com.fiec.lpiiiback.models.dto.LoginRequestDto;
import com.fiec.lpiiiback.models.dto.UserDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/books")
    public List<Book> readFinishedBooks(){
        return userService.readFinishedBooks();
    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody CreateUserRequestDto createUserRequestDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        userService.signUpUser(user.getId(), createUserRequestDto.getName(), createUserRequestDto.getPhoneNumber(), createUserRequestDto.getAge());


    }

}
