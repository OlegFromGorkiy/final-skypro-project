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
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final AdService adService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService,
                             UserService userService,
                             AdService adService, CommentMapper mapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.adService = adService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable(name = "id") int id,
                                                Authentication authentication) {
        Ad ad = adService.getById(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Comments comments = mapper.toComments(commentService.getCommentByAd(ad));
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> sendComment(@PathVariable(name = "id") int adId,
                                                  @RequestBody UpdateComment update,
                                                  Authentication authentication) {
        User author = userService.getByEmail(authentication.getName());
        Ad ad = adService.getById(adId);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Comment comment = commentService.createComment(ad, update, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCommentDTO(comment));
    }


    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "adId") int adId,
                                              @PathVariable(name = "commentId") int commentId,
                                              Authentication authentication) {
        Ad ad = adService.getById(adId);
        Comment comment = commentService.getCommentById(commentId);
        if (checkNotFound(ad, comment)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (userCheck(user, comment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> editComment(@PathVariable(name = "adId") int adId,
                                                  @PathVariable(name = "commentId") int commentId,
                                                  @RequestBody UpdateComment update,
                                                  Authentication authentication) {
        Ad ad = adService.getById(adId);
        Comment comment = commentService.getCommentById(commentId);
        if (checkNotFound(ad, comment)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getByEmail(authentication.getName());
        if (userCheck(user, comment)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(mapper.toCommentDTO(commentService
                .update(comment, update)));
    }
//попытка обойти дублирование кода. Неудачная.
    private boolean checkNotFound(Ad ad, Comment comment) {
        return ad == null || comment == null;
    }
    private boolean userCheck(User user, Comment comment) {
        return user.getRole() != Role.ADMIN && !user.equals(comment.getAuthor());
    }
}