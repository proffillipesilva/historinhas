package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;

import java.util.List;

/**
 * Representa os casos de uso
 */
public interface UserService {

    User login(String email);
    List<User> getAllUsers();
    User signUpUser(Integer userId, String name, String phoneNumber, Integer age);

    User updateUser(Integer userId, String name,  Integer age);

    void deleteUser(Integer userId);
    void assignImage(Integer userId, String filename);

    User getUserById(Integer userId);

    User createTempUser(String email, String name, String profileImage);


}
