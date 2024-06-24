package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByBookId(long id);

    Comment save(long id, String text, long bookId);
}
