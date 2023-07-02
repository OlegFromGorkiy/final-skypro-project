package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default CommentDTO toCommentDTO(Comment comment) {
        CommentDTO result = new CommentDTO();
        result.setAuthor(comment.getAuthor().getId());
        result.setAuthorImage(comment.getAuthor().getImage());
        result.setAuthorFirstName(comment.getAuthor().getFirstName());
        result.setCreatedAt(comment.getCreatedAt().getTime());
        result.setPk(comment.getId());
        result.setText(comment.getText());
        return result;
    }

    default Comment fromCommentDTO(CommentDTO comment) {
        Comment result = new Comment();
        //need to change with service
        User user = new User();
        user.setId(comment.getAuthor());
        user.setImage(comment.getAuthorImage());
        user.setFirstName(comment.getAuthorFirstName());
        result.setAuthor(user);
        result.setId(comment.getPk());
        result.setText(comment.getText());
        result.setCreatedAt(new Date(comment.getCreatedAt()));
        return result;
    }

    default ResponseWrapperComment toResponseWrapper(List<Comment> comments) {
        ResponseWrapperComment result = new ResponseWrapperComment();
        result.setCount(comments.size());
        result.setResult(comments.stream()
                .map(this::toCommentDTO)
                .collect(Collectors.toList()));
        return result;
    }
}
