package com.fiec.lpiiiback.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUserDetailsService {
    UserDetails loadByEmail(String email);
}
