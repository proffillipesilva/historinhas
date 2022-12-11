package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.models.entities.User;
import com.fiec.lpiiiback.models.repositories.BookRepository;
import com.fiec.lpiiiback.models.repositories.UserRepository;
import com.fiec.lpiiiback.services.BookService;
import com.fiec.lpiiiback.services.MessagingService;
import com.fiec.lpiiiback.services.ReviewerService;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Permissions;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class ReviewerServiceImpl implements ReviewerService {
    @Autowired
    GoogleDriveManager googleDriveManager;

    @Autowired
    MessagingService messagingService;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

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
    public void inviteWriter(User reviewer, Integer writerId, String bookId) throws GeneralSecurityException, IOException {
        User user = userRepository.findById(writerId).orElseThrow();
        Book book = bookService.getBookById(bookId);

        Permission permission = new Permission()
                //.setType("anyone")
                //.setRole("writer");
                .setType("user")
                .setEmailAddress(user.getEmail())
                .setRole("writer");
        googleDriveManager.getInstance().permissions().create(book.getDocsBook(), permission).execute();
        if(book.getAuthors() == null) book.setAuthors(new HashSet<>());

        book.getAuthors().add(user);
        //userRepository.save(user);
        bookRepository.save(book);

        try{
            messagingService.sendInviteMessageToUser(user.getId(), book, reviewer.getEmail());

        } catch(Exception e){
            log.error(e.getMessage());
        }

    }

    @Override
    public void finishBook(String bookId) throws GeneralSecurityException, IOException {
        Book book = bookService.finishBook(bookId);

        Drive.Permissions.List list = googleDriveManager.getInstance().permissions().list(book.getDocsBook());
        PermissionList permissionList = list.execute();
        List<Permission> permissions = permissionList.getPermissions();

            for (Permission perm : permissions) {
                try {
                    googleDriveManager.getInstance().permissions().delete(book.getDocsBook(), perm.getId()).execute();
                } catch(Exception e){
                    log.error(e.getMessage());
                }
            }

        Permission permission = new Permission()
                .setType("anyone")
                .setRole("reader");
        googleDriveManager.getInstance().permissions().create(book.getDocsBook(), permission).execute();
    }
}
