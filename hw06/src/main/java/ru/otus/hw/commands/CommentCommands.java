package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {
    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find comment by book id", key = "cbbid")
    public String findCommentByBookId(long id) {
        return commentService.findByBookId(id)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String addNewComment(String text, long bookId) {
        Comment comment = commentService.save(0, text, bookId);
        return commentConverter.commentToString(comment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String text, long bookId) {
        Comment comment = commentService.save(id, text, bookId);
        return commentConverter.commentToString(comment);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteCommentById(long id) {
        commentService.deleteById(id);
    }
}