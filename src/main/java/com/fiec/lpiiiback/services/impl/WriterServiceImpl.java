package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.WriterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterServiceImpl implements WriterService {
    @Override
    public void requestReview(String bookId) {

    }

    @Override
    public List<Book> getDocumentsForEdit() {
        return null;
    }
}
