package com.fiec.lpiiiback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
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

    @OneToMany
    List<User> authors = new ArrayList<>();


}
