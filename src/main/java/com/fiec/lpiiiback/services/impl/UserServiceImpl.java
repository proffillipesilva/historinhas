package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getProfile(String userId) {
        return userRepository.findById(Integer.parseInt(userId)).orElseThrow();

        //.profileImage("http://maisexpressao.com.br/imagens/noticias/45537/640x480/airltonjpg.JPG")

    }

    @Override
    public User login(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User signUpUser(String name, String email, String password, String cpf, String age, String lastName) {
        return userRepository.save(
                User.builder()
                        .name(name)
                        .cpf(cpf)
                        .email(email)
                        .age(age)
                        .lastName(lastName)
                        .password(new String(DigestUtils.sha3_256(password), StandardCharsets.UTF_8))
                        .build()
        );
    }

    @Override
    public User updateUser(Integer userId, String name, String password, String cpf, String lastName, String age) {
        User currentUser = userRepository.findById(userId).orElseThrow();
        currentUser.setName(name);
        currentUser.setPassword(password);
        currentUser.setCpf(cpf);
        currentUser.setAge(age);
        currentUser.setLastName(lastName);
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
    public List<Book> readFinishedBooks() {
        return null;
    }


}
