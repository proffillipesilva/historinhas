package com.fiec.lpiiiback.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.dto.BookResponseDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.BookService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        return bookService.findAllBooks().stream().map(BookResponseDto::convert).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BookResponseDto insertBook(@RequestParam("bookInfo") String bookRequest,
                                            @RequestParam("image") MultipartFile multipartFile
    ) throws IOException {

        BookRequestDto bookRequestDto = new ObjectMapper().readValue(bookRequest, BookRequestDto.class);

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
        Book insertedBook = bookService.insertNewBook(bookRequestDto, profileImage);
        return BookResponseDto.convert(insertedBook);

    }
}
