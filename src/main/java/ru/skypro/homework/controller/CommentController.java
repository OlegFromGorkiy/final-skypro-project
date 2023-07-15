package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final CommentMapper mapper;

    public CommentController(CommentMapper commentMapper,
                             CommentService commentService,
                             UserService userService,
                             CommentMapper mapper) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable(name = "id") int id) {
        Comments comments = commentMapper.toComments(commentService.getCommentOfAd(id));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> sendComment(@PathVariable(name = "id") Ad ad,
                                                  @RequestBody UpdateComment updateComment,
                                                  @RequestBody User author,
                                                  Authentication authentication) {
        CommentDTO comment = mapper.toCommentDTO(commentService.createComment(ad, updateComment, author));
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }


    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "adId") int adId,
                                              @PathVariable(name = "commentId") Comment commentId,
                                              Authentication authentication) {
        Collection<Comment> comment = commentService.getComments(adId);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (user.getRole() != Role.ADMIN && !user.equals(comment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> editComment(@PathVariable(name = "adId") int adId,
                                                  @PathVariable(name = "commentId") Comment commentId,
                                                  @RequestBody UpdateComment update,
                                                  Authentication authentication) {
        Comment comment = commentService.update(adId, commentId, update);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (user.getRole() != Role.ADMIN && !user.equals(comment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(mapper.toCommentDTO(commentService
                .update(adId, commentId, update)));
    }
}