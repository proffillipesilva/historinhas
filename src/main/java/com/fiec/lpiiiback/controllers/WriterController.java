package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.AuthorDto;
import com.fiec.lpiiiback.models.dto.FindWriterDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    WriterService writerService;

    @PostMapping ("/requestReview/{bookId}")
    public void requestReview (@PathVariable("bookId") String bookId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        writerService.requestReview(bookId, user);

    }
    @GetMapping("/books")
    public List<Book> getDocumentsForEdit(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return writerService.getDocumentsForEdit(user);

    }

    @PostMapping("/find")
    public AuthorDto findByEmail(@RequestBody @Valid FindWriterDto findWriterDto){
        User user = writerService.findByEmail(findWriterDto.getEmail());
        if(user != null){
            return AuthorDto.convert(user);
        } else return  null;
    }
}
