package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.AuthorDto;
import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.dto.ReviewerBookResponseDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAllFinishedBooks() {return bookRepository.findBooksByFinished(true);}

    @Override
    public Book insertNewBook(BookRequestDto bookRequestDto, String docsId, String bookImage, User user) {

        HashSet<User> myAuthors = new HashSet<>();
        myAuthors.add(user);
        Book book = bookRepository.save(
                Book.builder()
                        .description(bookRequestDto.getDescription())
                        .docsBook(docsId)
                        .name(bookRequestDto.getName())
                        .genre(bookRequestDto.getGenre())
                        .reviewerId(user.getId())
                        .authorName(user.getName())
                        .authors(myAuthors)
                        .bookImage(bookImage)
                        .build()
        );
        return bookRepository.save(book);

    }

    @Override
    public void updateFrontImage(String bookId, String profileImage) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setBookImage(profileImage);
        bookRepository.save(book);
    }

    @Override
    public Book finishBook(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setFinished(true);
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow();
    }

    @Override
    public List<Book> getMyBooks(User user) {
        return bookRepository.findBooksByAuthorsContainsAndFinishedFalse(user);
    }

    @Override
    public List<ReviewerBookResponseDto> getBooksByReviewerId(Integer reviewerId) {
        List<Book> books = bookRepository.findBooksByReviewerIdAndFinishedFalse(reviewerId);
        return books.stream().map(book -> {
            Set<User> users = book.getAuthors();
            Set<AuthorDto> authors = new HashSet<>();
            if(users != null){
                authors = users.stream().map(AuthorDto::convert).collect(Collectors.toSet());
            }
            ReviewerBookResponseDto reviewerBookResponseDto = ReviewerBookResponseDto.convert(book);
            reviewerBookResponseDto.setCoAuthors(authors);
            return reviewerBookResponseDto;

        }).collect(Collectors.toList());
    }
}
