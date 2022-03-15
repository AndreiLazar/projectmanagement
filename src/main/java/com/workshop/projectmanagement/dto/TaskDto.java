package com.workshop.projectmanagement.dto;

import com.workshop.projectmanagement.entity.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class TaskDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String title;
    private String description;
    private Integer userPoints;
    private Integer estimation;
    private Status status;
    private UserDto user;
    private UserStoryDto userStory;
}
