package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAdId(int id);

    Optional<Comment> findByIdAndAdId(int commentId, int adId);

    void deleteCommentById(Comment comment);
}
