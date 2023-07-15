package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {

    List<Comment> getAll();

    Comment addComment(int id, CommentDTO updateComment);

    boolean deleteComment(int commentId, int adId);

    Comment update(int id, int adId, UpdateComment updateComment);

    Collection<Comment> getComments(int adsId);
}
