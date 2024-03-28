package ru.otus.hw.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями")
@DataJpaTest
@Import({JpaCommentRepository.class, JpaBookRepository.class})
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaBookRepository bookRepository;
    @Autowired
    private JpaCommentRepository commentRepository;

    @DisplayName("Получение всех комментариев по id книги")
    @Test
    void findCommentsByBookId() {
        List<Comment> comment = commentRepository.findByBookId(2L);
        Assertions.assertEquals("Could have been better", comment.get(0).getText());
        Assertions.assertEquals("Very interesting book", comment.get(1).getText());
    }

    @DisplayName("Получение комментария по id")
    @Test
    void findCommentById() {
        Optional<Comment> optionalComment = commentRepository.findById(1);
        Assertions.assertTrue(optionalComment.isPresent());
        Assertions.assertEquals(1, optionalComment.get().getBook().getId());
        Assertions.assertEquals("Very interesting book", optionalComment.get().getText());
    }

    @DisplayName("Добавление нового комментария")
    @Test
    void addComment() {
        Book book = bookRepository.findById(3).get();
        var expectedComment = new Comment(0, "Boring story", book);
        commentRepository.save(expectedComment);
        List<Comment> comments = commentRepository.findByBookId(3);
        Assertions.assertEquals(2, comments.size());
        Assertions.assertEquals("Boring story", comments.get(1).getText());
        Assertions.assertEquals(3, comments.get(1).getBook().getId());
    }

    @DisplayName("Изменение комментария")
    @Test
    void updateComment() {
        Book book = bookRepository.findById(3).get();
        var expectedComment = new Comment(3, "Awesome!!!", book);
        commentRepository.save(expectedComment);
        List<Comment> comments = commentRepository.findByBookId(3);
        Assertions.assertEquals(1, comments.size());
        Assertions.assertEquals("Awesome!!!", comments.get(0).getText());
        Assertions.assertEquals(3, comments.get(0).getBook().getId());
    }

    @DisplayName("Удаление комментария")
    @Test
    @Transactional
    void deleteComment() {
        commentRepository.deleteById(2);
        Assertions.assertTrue(commentRepository.findById(2).isEmpty());
    }
}