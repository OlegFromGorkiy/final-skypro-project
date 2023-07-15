package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByAdId(int id);

    void deleteByIdAndAdId(int id, int adId);

    Optional<Comment> findByIdAndAdId(int id, int adId);

    void deleteCommentById(Comment comment);
}
