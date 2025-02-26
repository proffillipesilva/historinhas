package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ReviewerService {
    Book createDocument(BookRequestDto bookRequestDto, User user) throws GeneralSecurityException, IOException;

    void inviteWriter(User reviewer, Integer writerId, String bookId) throws GeneralSecurityException, IOException;

    void finishBook(String bookId) throws GeneralSecurityException, IOException;
}
