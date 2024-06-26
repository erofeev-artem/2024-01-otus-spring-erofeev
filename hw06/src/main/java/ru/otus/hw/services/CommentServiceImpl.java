package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(long bookId) {
        List<Comment> comments = new ArrayList<>();
        Book book = bookRepository.findById(bookId).get();
        for (Comment comment : book.getComments()) {
            long id = comment.getId();
            Optional<Comment> optionalById = commentRepository.findById(id);
            optionalById.ifPresent(comments::add);
        }
        return comments;
    }

    @Override
    @Transactional
    public Comment save(long id, String text, long bookId) {
        Book book = bookRepository.findById(bookId).get();
        Comment comment = new Comment(id, text, book);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
