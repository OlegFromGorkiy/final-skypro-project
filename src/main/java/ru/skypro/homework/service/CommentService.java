package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
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
     * Создание нового комментария
     */
    Comment createComment(Ad ad, UpdateComment updateComment, User author);

    /**
     * Удалить комментарий
     */
    void deleteComment(Comment comment);

    /**
     * Обновить комментарий
     */
    Comment update(int adId, Comment comment, UpdateComment updateComment);

    /**
     * Получить комментарий по объявлению
     */
    Collection<Comment> getComments(int adsId);
}