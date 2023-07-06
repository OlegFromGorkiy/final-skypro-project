package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CommentMapperTest {
    private static final User USER;
    private static final Comment COMMENT;

    //given
    static {
        USER = new User();
        USER.setId(1);
        USER.setImage("image");
        USER.setFirstName("firstName");

        COMMENT = new Comment();
        COMMENT.setAuthor(USER);
        COMMENT.setId(1);
        COMMENT.setText("text");
        COMMENT.setCreatedAt(new Date());
    }

    @Autowired
    CommentMapper mapper;

    @Test
    public void checkToCommentDTO() {
        //when
        CommentDTO result = mapper.toCommentDTO(COMMENT);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getAuthor(), COMMENT.getAuthor().getId());
        Assertions.assertEquals(result.getAuthorImage(), COMMENT.getAuthor().getImage());
        Assertions.assertEquals(result.getAuthorFirstName(), COMMENT.getAuthor().getFirstName());
        Assertions.assertEquals(result.getCreatedAt(), COMMENT.getCreatedAt().getTime());
        Assertions.assertEquals(result.getPk(), COMMENT.getId());
        Assertions.assertEquals(result.getText(), COMMENT.getText());
    }

    @Test
    public void checkFromCommentDTO() {
        //when
        Comment result = mapper.fromCommentDTO(mapper.toCommentDTO(COMMENT));
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getAuthor().getId(), COMMENT.getAuthor().getId());
        Assertions.assertEquals(result.getAuthor().getFirstName(), COMMENT.getAuthor().getFirstName());
        Assertions.assertEquals(result.getAuthor().getImage(), COMMENT.getAuthor().getImage());
        Assertions.assertEquals(result.getCreatedAt(), COMMENT.getCreatedAt());
        Assertions.assertEquals(result.getId(), COMMENT.getId());
        Assertions.assertEquals(result.getText(), COMMENT.getText());
    }

    @Test
    public void checkTOResponseWrapper() {
        //given
        List<Comment> comments = new LinkedList<>();
        comments.add(COMMENT);
        //when
        ResponseWrapperComment result = mapper.toResponseWrapper(comments);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCount(), comments.size());
        Assertions.assertEquals(result.getResult(),
                comments.stream()
                        .map(e -> mapper.toCommentDTO(e))
                        .collect(Collectors.toList()));
    }
}
