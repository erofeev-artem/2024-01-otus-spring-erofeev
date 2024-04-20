package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private long id;

    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}