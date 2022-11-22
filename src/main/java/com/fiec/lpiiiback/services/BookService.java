package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;


import java.util.List;

public interface BookService {

    List<Book> findAllBooks();
    Book insertNewBook(BookRequestDto bookRequestDto, String docsId, String bookImage);

}
