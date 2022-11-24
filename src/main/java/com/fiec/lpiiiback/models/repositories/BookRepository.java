package com.fiec.lpiiiback.models.repositories;

import com.fiec.lpiiiback.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findBooksByFinished(boolean finished);
}
