package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;

    private String title;

    @DBRef
    private Author author;

    @DBRef
    private Set<Genre> genres;

    public Book(String title, Author author, Set<Genre> genres) {
        this.title = title;
        this.author = author;
        this.genres = genres;
    }
}
