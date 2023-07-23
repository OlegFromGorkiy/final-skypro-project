package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundElement;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final AdService adService;
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    private final UserService userService;

    public CommentServiceImpl(AdService adService, CommentRepository commentRepository,
                              CommentMapper mapper, UserService userService) {
        this.adService = adService;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Comments getCommentByAdId(int adId) {
        Ad ad = adService.getById(adId);
        return mapper.toComments(commentRepository.findAllByAd(ad));
    }

    @Override
    public Comment getCommentById(int commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new NotFoundElement(commentId, Comment.class);
        }
        return optional.get();
    }

    @Override
    public CommentDTO createComment(int adId, UpdateComment update, Authentication authentication) {
        Ad ad = adService.getById(adId);
        User author = userService.getFromAuthentication(authentication);
        Comment comment = new Comment();
        comment.setText(update.getText());
        comment.setCreatedAt(new Date());
        comment.setAd(ad);
        comment.setAuthor(author);
        return mapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(int adId, int commentId, Authentication authentication) {
        Comment comment = getCommentById(commentId);
        adService.getById(adId);//проверяем есть ли объявление
        checkAuthor(comment, authentication);
        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO update(int adId, int commentId, UpdateComment update, Authentication authentication) {
        Comment comment = getCommentById(commentId);
        adService.getById(adId);//проверяем есть ли объявление
        checkAuthor(comment, authentication);
        comment.setText(update.getText());
        comment.setCreatedAt(new Date());
        return mapper.toCommentDTO(commentRepository.save(comment));
    }

    private void checkAuthor(Comment comment, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(comment.getAuthor())) {
            throw new ForbiddenException(String.format("%s can't edit or delete this comment", user.getEmail()));
        }
    }
}