package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentOfAd(int adId) {
        return commentRepository.findAllByAdId(adId);
    }

    @Override
    public Comment createComment(Ad ad, UpdateComment updateComment, User author) {
        Comment comment = new Comment();
        comment.setId(comment.getId());
        comment.setText(comment.getText());
        comment.setCreatedAt(new Date());
        comment.setAd(ad);
        comment.setAuthor(author);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.deleteCommentById(comment);
    }

    @Override
    public Comment update(int adId, Comment comment, UpdateComment updateComment) {
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