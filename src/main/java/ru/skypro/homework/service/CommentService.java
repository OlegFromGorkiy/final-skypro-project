package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.List;

public interface CommentService {

    /**
     * Получить список комментариев объявления
     *
     * @param adId id объявления
     * @return дто со списком комментариев
     */
    List<Comment> getCommentOfAd(int adId);

    /**
     * Получение комментария по id
     */
    Comment getCommentById(int commentId);

    /**
     * Создание нового комментария
     */
    Comment createComment(int id, UpdateComment updateComment, User author);

    /**
     * Удалить комментарий
     */
    void deleteComment(int commentId);

    /**
     * Обновить комментарий
     */
    Comment update(Comment comment, UpdateComment updateComment);

    /**
     * Получить комментарий по объявлению
     */
    Collection<Comment> getComments(int adsId);
}