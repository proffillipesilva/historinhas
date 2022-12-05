package com.fiec.lpiiiback.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String bookId;
    private String docsBook;
    private String name;
    private String description;
    private String genre;
    private String bookImage;
    private String authorName;
    private Integer reviewerId; //author
    private boolean finished;

    @ManyToMany
    @JoinTable(
            name = "tb_book_authors",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    Set<User> authors = new HashSet<>();


}
