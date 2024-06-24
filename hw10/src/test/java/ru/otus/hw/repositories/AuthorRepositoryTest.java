package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@DisplayName("Репозиторий на основе Spring Jpa для работы с авторами")
@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repository;

    @DisplayName("Получение всех авторов")
    @Test
    void findAllAuthors() {
        List<Author> all = repository.findAll();
        Assertions.assertEquals(3, all.size());
    }

    @DisplayName("Получение автора по id")
    @Test
    void findAuthorById() {
        Optional<Author> optional = repository.findById(2L);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(2, optional.get().getId());
        Assertions.assertEquals("Author_2", optional.get().getFullName());
    }
}