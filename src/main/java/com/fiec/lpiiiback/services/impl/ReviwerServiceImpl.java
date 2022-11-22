package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.BookService;
import com.fiec.lpiiiback.services.ReviwerService;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.drive.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.UUID;

@Service
public class ReviwerServiceImpl implements ReviwerService {
    @Autowired
    GoogleDriveManager googleDriveManager;

    @Autowired
    BookService bookService;
    @Override
    public Book createDocument(BookRequestDto bookRequestDto) throws GeneralSecurityException, IOException {

        com.google.api.services.docs.v1.model.Document doc = new  com.google.api.services.docs.v1.model.Document();

        doc.setTitle(bookRequestDto.getName());


        try {
            com.google.api.services.docs.v1.model.Document docResponse;
            Docs.Documents.Create create = googleDriveManager.getInstanceDocs().documents().create(doc);
            docResponse = create.execute();
            Permission permission = new Permission()
                    .setType("anyone")
                    .setRole("writer");
            googleDriveManager.getInstance().permissions().create(docResponse.getDocumentId(), permission).execute();

            //String DOCUMENT_ID = "prd" + UUID.randomUUID() + "_" + Long.toHexString(new Date().getTime());
            //com.google.api.services.docs.v1.model.Document response = googleDriveManager.getInstanceDocs().documents().get(DOCUMENT_ID).execute();
            //String title = response.getTitle();


            return bookService.insertNewBook(bookRequestDto, docResponse.getDocumentId(), "");
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void inviteWriter(String writerId, String bookId) {

    }

    @Override
    public void finishBook(String bookId) {

    }
}
