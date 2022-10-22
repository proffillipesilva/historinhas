package com.fiec.lpiiiback.models.dto;


import com.fiec.lpiiiback.models.entities.Book;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDto {
    private String bookId;

    private String name;
    private String description;
    private String genre;
    private String authorName;
    private String bookImage;

    public static BookResponseDto convert(Book book){

        return BookResponseDto.builder()
                .name(book.getName())
                .description(book.getDescription())
                .bookId(book.getBookId())
                .genre(book.getGenre())
                .authorName(book.getAuthorName())
                .bookImage(book.getBookImage())
                .build();
    }
}
