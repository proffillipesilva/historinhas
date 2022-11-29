package com.fiec.lpiiiback.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.dto.BookResponseDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.enums.UserRoles;
import com.fiec.lpiiiback.services.BookService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    public List<BookResponseDto> getAllBooks(){
        return bookService.findAllFinishedBooks().stream().map(BookResponseDto::convert).collect(Collectors.toList());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addFrontImage(@PathVariable("id") String bookId,
                                            @RequestParam("image") MultipartFile multipartFile
    ) throws IOException {



        String profileImage = "prd" + UUID.randomUUID() + "_" + Long.toHexString(new Date().getTime());

        Path filename = Paths.get("uploads").resolve(profileImage);
        Path thumbFilename = Paths.get("uploads").resolve("thumb_" + profileImage);
        Thumbnails.of(multipartFile.getInputStream())
                .size(500, 500)
                .outputFormat("jpg")
                .toFile(new File(filename.toString()));
        Thumbnails.of(multipartFile.getInputStream())
                .size(100, 100)
                .outputFormat("jpg")
                .toFile(new File(thumbFilename.toString()));
        bookService.updateFrontImage(bookId, profileImage);

    }

    @GetMapping("/written")
    public List<BookResponseDto> getMyWrittenBooks(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return bookService.getMyBooks(user).stream().map(BookResponseDto::convert).collect(Collectors.toList());
    }

    @GetMapping("/reviewer")
    public List<BookResponseDto> getBooksByReviewer(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if(UserRoles.ROLE_USER.equals(user.getUserRole())) return null;
        return bookService.getBooksByReviewerId(user.getId()).stream().map(BookResponseDto::convert).collect(Collectors.toList());
    }
}
