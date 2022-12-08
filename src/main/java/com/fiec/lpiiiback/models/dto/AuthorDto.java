package com.fiec.lpiiiback.models.dto;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
    private Integer authorId;
    private String authorName;
    private String authorEmail;

    public static AuthorDto convert(User user){

        return AuthorDto.builder()
                .authorId(user.getId())
                .authorName(user.getName())
                .authorEmail(user.getEmail())
                .build();
    }
}
