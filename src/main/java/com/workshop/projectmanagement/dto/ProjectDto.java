package com.workshop.projectmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProjectDto {
    private Integer id;
    private String name;
    private String description;
    private List<UserDto> userList;
}
