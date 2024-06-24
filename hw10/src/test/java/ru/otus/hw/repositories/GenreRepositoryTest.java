package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

@DisplayName("Репозиторий на основе Spring Jpa для работы с жанрами")
@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository repository;

    @DisplayName("Получение всех жанров")
    @Test
    void findAllGenres() {
        List<Genre> all = repository.findAll();
        Assertions.assertEquals(6, all.size());
    }

    @DisplayName("Получение нескольких жанров по id")
    @Test
    void findGenresById() {
        List<Genre> genres = repository.findAllById(Set.of(2L, 3L));
        Assertions.assertEquals("Genre_2", genres.get(0).getName());
        Assertions.assertEquals("Genre_3", genres.get(1).getName());
    }
}