package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.BookService;
import com.fiec.lpiiiback.services.ReviewerService;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    @Autowired
    GoogleDriveManager googleDriveManager;

    @Autowired
    BookService bookService;

    @Autowired
    UserRepository userRepository;
    @Override
    public Book createDocument(BookRequestDto bookRequestDto, User user) throws GeneralSecurityException, IOException {

        com.google.api.services.docs.v1.model.Document doc = new  com.google.api.services.docs.v1.model.Document();
        doc.setTitle(bookRequestDto.getName());
        com.google.api.services.docs.v1.model.Document docResponse;
        Docs.Documents.Create create = googleDriveManager.getInstanceDocs().documents().create(doc);
        docResponse = create.execute();

        Permission permission = new Permission()
                //.setType("anyone")
                //.setRole("writer");
                .setType("user")
                .setEmailAddress(user.getEmail())
                .setRole("writer");
        googleDriveManager.getInstance().permissions().create(docResponse.getDocumentId(), permission).execute();
        return bookService.insertNewBook(bookRequestDto, docResponse.getDocumentId(), "", user);

            //String DOCUMENT_ID = "prd" + UUID.randomUUID() + "_" + Long.toHexString(new Date().getTime());
            //com.google.api.services.docs.v1.model.Document response = googleDriveManager.getInstanceDocs().documents().get(DOCUMENT_ID).execute();
            //String title = response.getTitle();


    }

    @Override
    public void inviteWriter(Integer writerId, String bookId) throws GeneralSecurityException, IOException {
        User user = userRepository.findById(writerId).orElseThrow();
        Book book = bookService.getBookById(bookId);

        Permission permission = new Permission()
                //.setType("anyone")
                //.setRole("writer");
                .setType("user")
                .setEmailAddress(user.getEmail())
                .setRole("writer");
        googleDriveManager.getInstance().permissions().create(book.getDocsBook(), permission).execute();
        if(book.getAuthors() == null) book.setAuthors(new ArrayList<>());
        book.getAuthors().add(user);
        userRepository.save(user);
    }

    @Override
    public void finishBook(String bookId) throws GeneralSecurityException, IOException {
        Book book = bookService.finishBook(bookId);
        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        googleDriveManager.getInstance().permissions().create(book.getDocsBook(), permission).execute();
    }
}
