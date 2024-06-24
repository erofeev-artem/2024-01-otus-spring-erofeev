package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.rest.BookRestController;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void initBookData() {
        author1 = new Author(123L, "Author1");
        author2 = new Author(124L, "Author2");
        genre1 = new Genre(11, "Genre1");
        genre2 = new Genre(12, "Genre2");
    }

    @DisplayName("Получение всех книг")
    @Test
    void allBooks() throws Exception {
        Book starWars = new Book(11, "StarWars", author1, List.of(genre1, genre2));
        Book fightClub = new Book(11, "Fight club", author2, List.of(genre2));
        when(bookService.findAll()).thenReturn(List.of(starWars, fightClub));
        this.mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("StarWars")))
                .andExpect(content()
                        .string(containsString("Fight club")));
    }

    @DisplayName("Добавление новой книги")
    @Test
    void saveNewBook() throws Exception {
        when(authorService.findByFullName("Author1")).thenReturn(author1);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(0, "Big city", "Author1", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/book/save")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @DisplayName("Изменение книги")
    @Test
    void updateBook() throws Exception {
        when(authorService.findByFullName("Author2")).thenReturn(author2);
        when(genreService.findByGenreName(List.of("Genre1", "Genre2"))).thenReturn(List.of(genre1, genre2));
        BookDto bookDto = new BookDto(1, "Old town road", "Author2", List.of("Genre1", "Genre2"));
        this.mockMvc.perform(post("/book/save")
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @DisplayName("Удаление книги")
    @Test
    void deleteBook() throws Exception {
        this.mockMvc.perform(delete("/book/22")
                        .queryParam("id", "22"))
                .andExpect(status().is(204));
    }

    @DisplayName("Получение книги по id")
    @Test
    void getBookById() throws Exception {
        Book lordOfTheRings = new Book(33, "Lord of the rings", author1, List.of(genre1, genre2));
        when(bookService.findById(33)).thenReturn(lordOfTheRings);
        this.mockMvc.perform(get("/book/33")
                        .queryParam("id", "33"))
                .andExpect(status().is(200))
                .andExpect(content()
                        .json("{\"id\":33,\"title\":\"Lord of the rings\",\"authorName\":\"Author1\",\"genresNames\":[\"Genre1\",\"Genre2\"]}"));
    }
}