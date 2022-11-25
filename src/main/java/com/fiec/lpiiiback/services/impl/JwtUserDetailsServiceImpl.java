package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.JwtUserDetailsService;
import com.fiec.lpiiiback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadByEmail(String email) {
        return userService.login(email);
    }
}
