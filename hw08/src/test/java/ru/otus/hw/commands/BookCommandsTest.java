package ru.otus.hw.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Команды для работы с книгами")
@SpringBootTest
public class BookCommandsTest {
    @Autowired
    private BookCommands bookCommands;
    @Autowired
    private BookConverter bookConverter;

    private List<Author> dbAuthors;
    private List<Genre> dbGenres;
    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @DisplayName("Получаем список всех книг")
    @Test
    public void findAllBooks() {
        String actualBooks = bookCommands.findAllBooks();

        assertThat(actualBooks)
        String expectedBooks = dbBooks.stream()
                .map(book -> bookConverter.bookToString(book))
                .collect(Collectors.joining(",\n"));
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @DisplayName("Получаем книги по id")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    public void findBookById(Book expectedBook) {
        var actualBook = bookCommands.findBookById(expectedBook.getId());
        assertThat(actualBook).isEqualTo(bookConverter.bookToString(expectedBook));
    }

    @DisplayName("Добавляем новую книгу")
    @Test
    public void insertBook() {
        String expectedBook = bookCommands.insertBook("Test title", "2", Set.of("2", "3"));
        String bookId = expectedBook.substring(4, 5);
        String actualBook = bookCommands.findBookById(bookId);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Изменяем существующую книгу")
    @Test
    public void updateBook() {
        String expectedBook = bookCommands.updateBook("3", "Test title", "2", Set.of("2", "3"));
        String actualBook = bookCommands.findBookById("3");
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Удаляем существующую книгу")
    @Test
    public void deleteBook() {
        bookCommands.deleteBook("2");
        String result = bookCommands.findBookById("2");
        assertThat(result).isEqualTo("Book with id 2 not found");
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author("id", "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
//        return IntStream.range(1, 7).boxed()
//                .map(id -> new Genre(id, "Genre_" + id))
//                .toList();
        return null;
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id,
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2)
                ))
                .toList();
        return null;
        new Book("6626c1d29870465b3e5b28af", "Fahrenheit 451",
                new Author("6626c1d09870465b3e5b28a7","Author_1"),
                List.of(new Genre("6626c1d19870465b3e5b28ab", "Genre_1")))
        Id: 6626c1d29870465b3e5b28af, title: Fahrenheit 451, author: {Id: 6626c1d09870465b3e5b28a7, FullName: Author_1}, genres: [{Id: 6626c1d19870465b3e5b28ab, Name: Genre_1}, {Id: 6626c1d29870465b3e5b28ad, Name: Genre_3}],
        Id: 6626c1d39870465b3e5b28b0, title: Atlas shrugged, author: {Id: 6626c1d19870465b3e5b28a8, FullName: Author_2}, genres: [{Id: 6626c1d29870465b3e5b28ad, Name: Genre_3}],
        Id: 6626c1d39870465b3e5b28b1, title: Starship troopers, author: {Id: 6626c1d19870465b3e5b28a9, FullName: Author_3}, genres: [{Id: 6626c1d19870465b3e5b28ab, Name: Genre_1}, {Id: 6626c1d29870465b3e5b28ad, Name: Genre_3}]
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}
