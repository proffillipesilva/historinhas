package com.fiec.lpiiiback.services;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;


import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    Book insertNewBook(BookRequestDto bookRequestDto, String docsId, String bookImage, User user);

    void updateFrontImage(String bookId, String profileImage);

    Book finishBook(String bookId);

    Book getBookById(String bookId);


}
