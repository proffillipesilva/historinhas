package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.MessagingService;
import com.fiec.lpiiiback.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessagingService messagingService;

    @Override
    public void requestReview(String bookId, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        messagingService.sendRequestReviewMessageToReviewer(book.getReviewerId(), book, user.getEmail());

    }

    @Override
    public List<Book> getDocumentsForEdit(User user) {
        return bookRepository.findBooksByAuthorsContainsAndFinishedFalse(user);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
