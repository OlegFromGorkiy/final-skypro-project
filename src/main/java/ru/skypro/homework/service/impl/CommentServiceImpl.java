package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentByAd(Ad ad) {
        return commentRepository.findAllByAd(ad);
    }

    @Override
    public Comment getCommentById(int commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        return optional.isEmpty() ? null : optional.get();
    }

    @Override
    public Comment createComment(Ad ad, UpdateComment update, User author) {
        Comment comment = new Comment();
        comment.setText(update.getText());
        comment.setCreatedAt(new Date());
        comment.setAd(ad);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment update(Comment comment, UpdateComment update) {
        comment.setText(update.getText());
        comment.setCreatedAt(new Date());
        return commentRepository.save(comment);
    }
}