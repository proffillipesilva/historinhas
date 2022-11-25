package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public User login(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User signUpUser(Integer userId, String name, String phoneNumber, Integer age) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(name);
        user.setAge(age);
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, String name, Integer age) {
        User currentUser = userRepository.findById(userId).orElseThrow();
        currentUser.setName(name);
        currentUser.setAge(age);
        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void assignImage(Integer userId, String filename) {
        User currentUser = userRepository.findById(userId).orElseThrow();
        currentUser.setProfileImage(filename);
        userRepository.save(currentUser);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public User createTempUser(String email, String name, String profileImage) {
        return userRepository.save(User.builder()
                        .email(email)
                        .alreadyRegistered(false)
                        .name(name)
                        .profileImage(profileImage)
                .build());
    }


}
