package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.ReviwerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviewers")
public class ReviewController {

    @Autowired
    ReviwerService reviwerService;

    @PostMapping("/createBook")
    public Book createDocument(@RequestBody BookRequestDto bookRequestDto){
        return reviwerService.createDocument(bookRequestDto);

    }
    @PostMapping("/inviteWriter/{writerId}/book/{bookId}")
    public void inviteWriter(@PathVariable("writerId") String writerId,@PathVariable("bookId") String bookId){
        reviwerService.inviteWriter(writerId,bookId);
    }
    @PostMapping("/finishBook/{bookId}")
    public void finishBook(@PathVariable("bookId") String bookId){
        reviwerService.finishBook(bookId);
    }
}
