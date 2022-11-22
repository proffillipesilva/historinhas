package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;
    @Override
    public List<Book> findAllBooks() {return bookRepository.findAll();}

    @Override
    public Book insertNewBook(BookRequestDto bookRequestDto,String docsId, String bookImage) {
        return bookRepository.save(
                Book.builder()
                        .description(bookRequestDto.getDescription())
                        .docsBook(docsId)
                        .name(bookRequestDto.getName())
                        .genre(bookRequestDto.getGenre())
                        .authorName(bookRequestDto.getAuthorName())
                        .bookImage(bookImage)
                        .build()
        );
    }
}
