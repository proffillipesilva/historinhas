package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;

public interface MessagingService {

    void registerFcmToken(User user, String fcmToken);

    void sendMessageToUser(Integer userId);

    void sendReviewMessageToUser(Integer userId, Book book, String requesterEmail);
}