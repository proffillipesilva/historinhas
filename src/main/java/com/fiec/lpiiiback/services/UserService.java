package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;

import java.util.List;

/**
 * Representa os casos de uso
 */
public interface UserService {
    User getProfile(String userId);
    User login(String email);
    List<User> getAllUsers();
    User signUpUser(String name, String email, String password, String cpf, String age, String lastName);

    User updateUser(Integer userId, String name, String password, String cpf, String lastName, String age);

    void deleteUser(Integer userId);
    void assignImage(Integer userId, String filename);

    User getUserById(Integer userId);

    List<Book> readFinishedBooks();
}
