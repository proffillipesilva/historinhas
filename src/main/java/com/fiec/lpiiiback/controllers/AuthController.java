package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.AuthRequestDto;
import com.fiec.lpiiiback.models.dto.LoginGoogleRequestDto;
import com.fiec.lpiiiback.models.dto.LoginResponseDto;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.enums.UserRoles;
import com.fiec.lpiiiback.services.FirebaseService;
import com.fiec.lpiiiback.services.JwtUserDetailsService;
import com.fiec.lpiiiback.services.UserService;
import com.fiec.lpiiiback.utils.JwtTokenUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    FirebaseService firebaseService;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    UserService userService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /*@PostMapping("/signUp")
    public void signUp(@RequestBody AuthRequestDto authRequestDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();


    } */



    @PostMapping("/signIn")
    public LoginResponseDto signInWithGoogle(@Valid @RequestBody LoginGoogleRequestDto loginGoogleRequestDto) throws GeneralSecurityException, IOException, HttpException {
        if(!clientId.equals(loginGoogleRequestDto.getClientId())){
            throw new HttpException();
        }

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleTokenResponse = verifier.verify(loginGoogleRequestDto.getTokenId());

        if(googleTokenResponse != null){
            GoogleIdToken.Payload payload = googleTokenResponse.getPayload();

            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            User user = (User) jwtUserDetailsService.loadByEmail(email);
            // Cria usuario quando nao tem na base ainda
            if(user == null){
                user = userService.createTempUser(email, name, pictureUrl);
            }
            // Seta role
            if(loginGoogleRequestDto.isReviewer()){
                user.setUserRole(UserRoles.ROLE_REVISOR);
            } else {
                user.setUserRole(UserRoles.ROLE_USER);
            }

            // gera token a partir do usuario e da role
            String token = jwtTokenUtil.generateToken(user);
            return LoginResponseDto.builder()
                    .profileImage(pictureUrl)
                    .token(token)
                    .name(name)
                    .alreadyRegistered(user.isAlreadyRegistered())
                    .build();
        }
        throw new HttpException();
    }
}