package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.MessagingService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessagingServiceImpl implements MessagingService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void registerFcmToken(User user, String fcmToken) {
        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }

    @Override
    public void sendMessageToUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        String fcmToken = user.getFcmToken();
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Oi Amiguinhos")
                        .setBody("Tudo bem, aqui é o Cereia").build()
                )
                .setToken(fcmToken)
                .build();
        try {
            firebaseMessaging.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendInviteMessageToUser(Integer userId, Book book, String reviewerEmail) {
        User user = userRepository.findById(userId).orElseThrow();
        String fcmToken = user.getFcmToken();
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("You were invited to edit!")
                        .setBody("From user " + book.getAuthorName() + " / " + reviewerEmail + " for book " + book.getName()).build()
                )
                .setToken(fcmToken)
                .build();
        try {
            firebaseMessaging.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendRequestReviewMessageToReviewer(Integer userId, Book book, String requesterEmail) {
        User author = userRepository.findById(userId).orElseThrow();
        String fcmToken = author.getFcmToken();
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Request review Message")
                        .setBody("From user " + requesterEmail + " for book " + book.getName()).build()
                )
                .setToken(fcmToken)
                .build();
        try {
            firebaseMessaging.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendFinishBookMessageToAuthors(Book book) {
        Set<User> authors = book.getAuthors();

        if(authors == null) return;
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        authors.forEach(u -> {

            Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("The reviewer concluded the book")
                        .setBody("The book " + book.getName() + " was concluded by reviewer " + book.getAuthorName()).build()
                )
                .setToken(u.getFcmToken())
                .build();

            try {
                firebaseMessaging.send(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}