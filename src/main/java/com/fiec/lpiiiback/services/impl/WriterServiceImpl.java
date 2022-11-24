package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    UserRepository userRepository;
    @Override
    public void requestReview(String bookId) {

    }

    @Override
    public List<Book> getDocumentsForEdit(User user) {

        return user.getBooks().stream().filter(b -> !b.isFinished()).collect(Collectors.toList());
    }
}
