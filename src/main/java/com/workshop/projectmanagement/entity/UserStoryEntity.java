package com.workshop.projectmanagement.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_story")
public class UserStoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String title;
    private String description;
    private Integer userPoints;
    private Integer estimation;
    private Status status;

    @ManyToOne
    private UserEntity user;
}
