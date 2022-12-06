package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.User;

public interface MessagingService {

    void registerFcmToken(User user, String fcmToken);

    void sendMessageToUser(Integer userId);
}