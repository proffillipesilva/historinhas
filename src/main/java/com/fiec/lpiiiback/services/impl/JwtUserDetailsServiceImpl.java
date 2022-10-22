package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
