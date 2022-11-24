package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface WriterService {
    void requestReview (String bookId);
    List<Book> getDocumentsForEdit(User user);
}
