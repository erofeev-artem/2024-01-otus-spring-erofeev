package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с комментариями")
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @DisplayName("Получаем комментарий по id книги")
    @Test
    public void findByBookId() {
        List<Comment> actualComments = commentService.findByBookId(2);
        String actualText = actualComments.get(1).getText();
        assertThat(actualText).isEqualTo("Very interesting book");
    }

    @DisplayName("Сохраняем комментарий")
    @Test
    public void save() {
        Comment actualComment = commentService.save(5, "Old comment", 3);
        assertThat(actualComment.getText()).isEqualTo("Old comment");
        assertThat(actualComment.getId()).isEqualTo(5);
        assertThat(actualComment.getBook().getId()).isEqualTo(3);
    }
}

