package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.UpdateComment;

import ru.skypro.homework.entity.Comment;

/**
 * Сервис для работы с комментариями
 */
public interface CommentService {

    /**
     * Получить список комментариев объявления
     *
     * @param adId идентификатор объявление по которому осуществляется выборка
     * @return DTO со списком комментариев
     */
    Comments getCommentByAdId(int adId);

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
     * @param adId           идентификатор объявления которое комментируют
     * @param update         текст комментария в виде объекта DTO
     * @param authentication данные авторизированного пользователя, автора комментария
     * @return созданный коментарий в виде объекта DTO
     */
    CommentDTO createComment(int adId, UpdateComment update, Authentication authentication);

    /**
     * Удаление комментария
     *
     * @param adId           идентификатор объявления которому принадлежит комментарий
     * @param commentId      идетификатор удаляемого комментария
     * @param authentication данные авторизированного пользователя, который удаляет комментарий
     */
    void deleteComment(int adId, int commentId, Authentication authentication);

    /**
     * Обновление комментария
     *
     * @param adId           идентификатор объявления которому принадлежит комментарий
     * @param commentId      идетификатор обновляемого комментария
     * @param update         данные для обновления комментария в виде объекта DTO
     * @param authentication данные авторизированного пользователя, который обновляет комментарий
     * @return обновленный коментарий в виде объекта DTO
     */
    CommentDTO update(int adId, int commentId, UpdateComment update, Authentication authentication);
}