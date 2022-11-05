package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviwerService {
    Book createDocument(BookRequestDto bookRequestDto);

    void inviteWriter(String writerId,String bookId);

    void finishBook(String bookId);
}
