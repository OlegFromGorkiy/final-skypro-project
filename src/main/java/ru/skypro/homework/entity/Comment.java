package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //int primitive in DTO
    private Integer id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "ad")
    private Ad ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

}
