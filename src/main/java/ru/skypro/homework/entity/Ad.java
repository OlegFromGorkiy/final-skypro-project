package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //int primitive in DTO
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinColumn(name = "comments_id")
    private List<Comment> comments;
}
