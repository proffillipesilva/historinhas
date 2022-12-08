package com.fiec.lpiiiback.models.dto;


import com.fiec.lpiiiback.models.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewerBookResponseDto {
    private String bookId;
    private String docsBook;
    private String name;
    private String description;
    private String genre;
    private String authorName;
    private String bookImage;
    private Set<AuthorDto> coAuthors;

    public static ReviewerBookResponseDto convert(Book book){

        return ReviewerBookResponseDto.builder()
                .name(book.getName())
                .description(book.getDescription())
                .bookId(book.getBookId())
                .genre(book.getGenre())
                .authorName(book.getAuthorName())
                .bookImage(book.getBookImage())
                .docsBook(book.getDocsBook())
                .build();
    }
}
