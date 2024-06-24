package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/book/{bookId}")
    public List<String> commentsByBookId(@PathVariable("bookId") long bookId) {
        return commentService.findByBookId(bookId).stream().map(Comment::getText).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveComment(@RequestBody Map<String, String> commentData) {
        commentService.save(0, commentData.get("text"), Long.parseLong(commentData.get("bookId")));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}