package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdService adService;

    public CommentServiceImpl(CommentRepository commentRepository, AdService adService) {
        this.commentRepository = commentRepository;
        this.adService = adService;
    }

    public List<Comment> getCommentOfAd(int adId) {
        return commentRepository.findAllByAdId(adId);
    }

    @Override
    public Comment getCommentById(int commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        return optional.isEmpty() ? null : optional.get();
    }

    @Override
    public Comment createComment(int id, UpdateComment updateComment, User author) {
        Comment comment = new Comment();
        comment.setText(comment.getText());
        comment.setCreatedAt(new Date());
        comment.setAd(adService.getById(id));
        comment.setAuthor(author);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment update(Comment comment, UpdateComment updateComment) {
        comment.setText(updateComment.getText());
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Collection<Comment> getComments(int adsId) {
        return commentRepository.findAllByAdId(adsId);
    }
}