package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment addComment(int id, CommentDTO updateComment) {
        Comment comment = commentMapper.fromCommentDTO(updateComment);
        comment.setId(comment.getId());
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public boolean deleteComment(int commentId, int adId) {
        commentRepository.deleteByIdAndAdId(commentId, adId);
        return false;
    }

    @Override
    public Comment update(int id, int adId, UpdateComment updateComment) {
        Comment comment = commentRepository.findByIdAndAdId(id, adId).orElseThrow();
        comment.setText(updateComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    public Collection<Comment> getComments(int adsId) {
        return commentRepository.findAllByAdId(adsId);
    }
}