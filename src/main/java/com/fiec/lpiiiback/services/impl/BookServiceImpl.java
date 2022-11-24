package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;
    @Override
    public List<Book> findAllBooks() {return bookRepository.findAll();}

    @Override
    public Book insertNewBook(BookRequestDto bookRequestDto, String docsId, String bookImage, User user) {

        return bookRepository.save(
                Book.builder()
                        .description(bookRequestDto.getDescription())
                        .docsBook(docsId)
                        .name(bookRequestDto.getName())
                        .genre(bookRequestDto.getGenre())
                        .reviewerId(user.getId())
                        .bookImage(bookImage)
                        .build()
        );
    }

    @Override
    public void updateFrontImage(String bookId, String profileImage) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setBookImage(profileImage);
        bookRepository.save(book);
    }

    @Override
    public void finishBook(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setFinished(true);
        bookRepository.save(book);
    }

    @Override
    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow();
    }
}
