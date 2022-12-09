package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;

public interface MessagingService {

    void registerFcmToken(User user, String fcmToken);

    void sendMessageToUser(Integer userId);

    void sendRequestReviewMessageToReviewer(Integer userId, Book book, String requesterEmail);

    void sendInviteMessageToUser(Integer userId, Book book, String reviewerEmail);

    void sendFinishBookMessageToAuthors(Book book);
}