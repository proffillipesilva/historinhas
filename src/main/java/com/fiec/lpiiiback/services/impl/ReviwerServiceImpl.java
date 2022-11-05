package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.ReviwerService;
import org.springframework.stereotype.Service;

@Service
public class ReviwerServiceImpl implements ReviwerService {
    @Override
    public Book createDocument(BookRequestDto bookRequestDto) {
        return null;
    }

    @Override
    public void inviteWriter(String writerId, String bookId) {

    }

    @Override
    public void finishBook(String bookId) {

    }
}
