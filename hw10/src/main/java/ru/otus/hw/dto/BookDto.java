package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {
    private long id;

    private String title;

    private String authorName;

    private List<String> genresNames;

    public static BookDto toDto(Book book) {
        List<String> genresNames = book.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getFullName(), genresNames);
    }
}
