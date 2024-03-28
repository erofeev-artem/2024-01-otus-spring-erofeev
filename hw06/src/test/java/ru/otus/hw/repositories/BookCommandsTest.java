package ru.otus.hw.repositories;

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
import ru.otus.hw.commands.BookCommands;
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
@AutoConfigureTestDatabase
@SpringBootTest
@Import({BookCommands.class})
@Transactional
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
        String expectedBook = bookCommands.insertBook("Test title", 2, Set.of(2L, 3L));
        String bookId = expectedBook.substring(4, 5);
        String actualBook = bookCommands.findBookById(Long.parseLong(bookId));
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Изменяем существующую книгу")
    @Test
    public void updateBook() {
        String expectedBook = bookCommands.updateBook(3, "Test title", 2, Set.of(2L, 3L));
        String actualBook = bookCommands.findBookById(3);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("Удаляем существующую книгу")
    @Test
    public void deleteBook() {
        bookCommands.deleteBook(2);
        String result = bookCommands.findBookById(2);
        assertThat(result).isEqualTo("Book with id 2 not found");
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id,
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2)
                ))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}
