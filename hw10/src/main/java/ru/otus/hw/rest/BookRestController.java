package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/all")
    public List<BookDto> allBooks() {
        List<Book> books = bookService.findAll();
        return books.stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author")
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/genre")
    public List<Genre> getGenres() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return BookDto.toDto(bookService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody BookDto book) {
        HttpStatus httpStatus;
        Author author = authorService.findByFullName(book.getAuthorName());
        List<Genre> genreList = genreService.findByGenreName(book.getGenresNames());
        Set<Long> genreIdSet = genreList.stream().map(Genre::getId).collect(Collectors.toSet());
        if (book.getId() != 0) {
            bookService.update(book.getId(), book.getTitle(), author.getId(), genreIdSet);
            httpStatus = HttpStatus.OK;
        } else {
            bookService.insert(book.getTitle(), author.getId(), genreIdSet);
            httpStatus = HttpStatus.CREATED;
        }
        return ResponseEntity.status(httpStatus)
                .build();
    }
}
