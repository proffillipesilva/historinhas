package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.CreateUserRequestDto;
import com.fiec.lpiiiback.models.dto.LoginRequestDto;
import com.fiec.lpiiiback.models.dto.UserDto;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    /*
    Aqui eu estou injetando a dependencia, que eh o atributo userService
    public UserController(UserService userService){
        this.userService = userService;
    }
    */

    @Autowired  // injecao de dependencia + inversao de controle
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){

        return userService.getAllUsers().stream().map(UserDto::convertToUserDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto signUpUser(@RequestBody CreateUserRequestDto createUserRequestDto){

        return UserDto.convertToUserDto(userService.signUpUser(
                createUserRequestDto.getName(),
                createUserRequestDto.getEmail(),
                createUserRequestDto.getPassword(),
                createUserRequestDto.getCpf(),
                createUserRequestDto.getAge(),
                createUserRequestDto.getLastName()

        ));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto){

        User user = userService.login(
                loginRequestDto.getEmail()
        );
        if(user == null){
            return ResponseEntity.status(Response.SC_UNAUTHORIZED).build();
        } else {
            return ResponseEntity.status(Response.SC_OK).build();
        }
    }

    @GetMapping("/{userId}")
    public UserDto getProfile(@PathVariable("userId") String userId){

        return UserDto.convertToUserDto(userService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    public UserDto editUser(@RequestBody CreateUserRequestDto createUserRequestDto, @PathVariable("userId") Integer userId){
        return UserDto.convertToUserDto(userService.updateUser(userId,
                createUserRequestDto.getName(),
                createUserRequestDto.getPassword(),
                createUserRequestDto.getCpf(),
                createUserRequestDto.getLastName(),
                createUserRequestDto.getAge()

        ));

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId){
        userService.deleteUser(userId);

    }

    @PutMapping(value = "/image/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void assignImageToUser(@PathVariable("userId") Integer userId, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String profileImage = UUID.randomUUID() + "_" + Long.toHexString(new Date().getTime());

        Path filename = Paths.get("uploads").resolve(profileImage);
        Path thumbFilename = Paths.get("uploads").resolve("thumb_" + profileImage);
        Thumbnails.of(multipartFile.getInputStream())
                .size(500, 500)
                .outputFormat("jpg")
                .toFile(new File(filename.toString()));
        Thumbnails.of(multipartFile.getInputStream())
                .size(100, 100)
                .outputFormat("jpg")
                .toFile(new File(thumbFilename.toString()));
        userService.assignImage(userId, profileImage);
    }

    @PostMapping(value="/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createBulkOfUsers(@RequestParam("csvFile") MultipartFile multipartFile ) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
        final int NAME=0, EMAIL=1, PASSWORD=2, CPF=3, LASTNAME=4, AGE=5;
        try (Reader reader = fileReader) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                List<String[]> csvFields =  csvReader.readAll();
                for(int i=1; i<csvFields.size(); i++){
                    User newUser = User.builder()
                            .email(csvFields.get(i)[EMAIL])
                            .name(csvFields.get(i)[NAME])
                            .password(csvFields.get(i)[PASSWORD])
                            .cpf(csvFields.get(i)[CPF])
                            .lastName(csvFields.get(i)[LASTNAME])
                            .age(csvFields.get(i)[AGE])
                            .build();
                    userService.signUpUser(newUser.getName(),
                            newUser.getEmail(),
                            newUser.getPassword(), newUser.getCpf(),
                            newUser.getAge(),
                            newUser.getLastName());
                }


            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
