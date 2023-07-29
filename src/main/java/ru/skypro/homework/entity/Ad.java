package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Класс объявления, данные хранятся в базе данных
 */
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
    /**
     * path to image
     */
    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "ad", orphanRemoval = true)
    private List<Comment> comments;
}
