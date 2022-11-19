package com.fiec.lpiiiback.services.impl;

import com.fiec.lpiiiback.models.dto.BookRequestDto;
import com.fiec.lpiiiback.models.entities.Book;
import com.fiec.lpiiiback.services.ReviwerService;
import com.google.api.services.docs.v1.Docs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class ReviwerServiceImpl implements ReviwerService {
    @Autowired
    GoogleDriveManager googleDriveManager;
    @Override
    public Book createDocument(BookRequestDto bookRequestDto) throws GeneralSecurityException, IOException {

        com.google.api.services.docs.v1.model.Document doc = new  com.google.api.services.docs.v1.model.Document();
        doc.setTitle(bookRequestDto.getName());
        try {
            Docs.Documents.Create create = googleDriveManager.getInstanceDocs().documents().create(doc);

            System.out.println(create);
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
