package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.FcmTokenDto;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessagingController {

    @Autowired
    private MessagingService messagingService;

    @PostMapping("/register")
    public void registerFcmToken(@RequestBody FcmTokenDto fcmTokenDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String fcmToken = fcmTokenDto.getFcmToken();
        messagingService.registerFcmToken(user, fcmToken);
    }

    @PostMapping("/{userId}")
    public void sendMessageTo(@PathVariable("userId") Integer userId){
        messagingService.sendMessageToUser(userId);
    }
}