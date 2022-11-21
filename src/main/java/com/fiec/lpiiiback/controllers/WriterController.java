package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    WriterService writerService;

    @PostMapping ("/requestReview/{bookId}")
    public void requestReview (@PathVariable("bookId") String bookId){
        writerService.requestReview(bookId);
    }
    @GetMapping("/books")
    public List<Book> getDocumentsForEdit() {
        return writerService.getDocumentsForEdit();

    }
}
