package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.Book;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @DisplayName("Сохраняем книгу")
    @Test
    public void insert() {
        Book actualBook = bookService.insert("The Door into Summer", 2, Set.of(2L, 3L));
        assertThat(actualBook.getTitle()).isEqualTo("The Door into Summer");
    }

    @DisplayName("Редактируем книгу")
    @Test
    public void update() {
        Book actualBook = bookService.update(2, "Starship Troopers", 2, Set.of(2L, 3L));
        assertThat(actualBook.getId()).isEqualTo(2);
        assertThat(actualBook.getTitle()).isEqualTo("Starship Troopers");
    }
}