package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;

public interface CommentService {

    /**
     * Получить список комментариев объявления
     *
     * @param ad объявление по которому осуществляется выборка
     * @return дто со списком комментариев
     */
    List<Comment> getCommentByAd(Ad ad);

    /**
     * Получение комментария по id
     *
     * @param commentId id комментария
     * @return найденный комментарий
     */
    Comment getCommentById(int commentId);

    /**
     * Создание нового комментария и сохраниение его в базу данных
     *
     * @param ad     комментируемое объявление
     * @param update текст комментария в виде объекта DTO
     * @param author автор комментария
     * @return созданный коментарий
     */
    Comment createComment(Ad ad, UpdateComment update, User author);

    /**
     * Удалить комментарий
     */
    void deleteComment(int commentId);

    /**
     * Обновление текста комментария и сохранение его в базу данных
     *
     * @param comment обновляемый комментарий
     * @param update  текст комментария в виде объекта DTO
     * @return обновленный комментарий
     */
    Comment update(Comment comment, UpdateComment update);
}