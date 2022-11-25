package com.fiec.lpiiiback.controllers;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.services.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/reviewers")
public class ReviewController {

    @Autowired
    ReviewerService reviewerService;

    @PostMapping("/createBook")
    public Book createDocument(@RequestBody BookRequestDto bookRequestDto, Authentication authentication) throws GeneralSecurityException, IOException {
        User user = (User) authentication.getPrincipal();
        return reviewerService.createDocument(bookRequestDto, user);

    }
    @PostMapping("/inviteWriter/{writerId}/book/{bookId}")
    public void inviteWriter(@PathVariable("writerId") Integer writerId,@PathVariable("bookId") String bookId) throws GeneralSecurityException, IOException {
        reviewerService.inviteWriter(writerId,bookId);
    }
    @PutMapping("/finishBook/{bookId}")
    public void finishBook(@PathVariable("bookId") String bookId) throws GeneralSecurityException, IOException {
        reviewerService.finishBook(bookId);
    }
}
