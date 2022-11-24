package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ReviwerService {
    Book createDocument(BookRequestDto bookRequestDto, User user) throws GeneralSecurityException, IOException;

    void inviteWriter(Integer writerId,String bookId);

    void finishBook(String bookId);
}
