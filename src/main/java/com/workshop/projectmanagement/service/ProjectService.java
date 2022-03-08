package com.workshop.projectmanagement.service;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.workshop.projectmanagement.dto.ProjectDto;
import com.workshop.projectmanagement.dto.ProjectPatchNameDto;
import com.workshop.projectmanagement.dto.UserDto;
import com.workshop.projectmanagement.entity.ProjectEntity;
import com.workshop.projectmanagement.entity.UserEntity;
import com.workshop.projectmanagement.repo.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private ProjectRepository projectRepository;
    private UserService userService;

    public ProjectService(ProjectRepository projectRepository, UserService userService){
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public ProjectDto createProject(ProjectDto projectDto){
        ProjectEntity saveProject = projectRepository.save(mapper.map(projectDto, ProjectEntity.class));

        return mapper.map(saveProject, ProjectDto.class);
    }

    public ProjectDto updateProject(ProjectDto projectDto){
        ProjectEntity saveProject = projectRepository.save(mapper.map(projectDto, ProjectEntity.class));

        return mapper.map(saveProject, ProjectDto.class);
    }

    public ProjectDto patchProject(ProjectPatchNameDto projectPatchNameDto){
        ProjectEntity projectRepositoryById = projectRepository.getById(projectPatchNameDto.getId() );
        projectRepositoryById.setName(projectPatchNameDto.getName());

        ProjectEntity patchedProject = projectRepository.save(projectRepositoryById);

        return mapper.map(patchedProject, ProjectDto.class);
    }

    public ProjectDto getProject(Integer id) {
        ProjectEntity projectEntity = projectRepository.getById(id);
        ProjectDto projectDto = mapper.map(projectEntity, ProjectDto.class);

        List<Integer> userIdList = projectEntity.getUserList()
                        .stream().map(UserEntity::getId)
                        .collect(Collectors.toList());
        List<UserDto> userDtoList = userService.getByUserIdList(userIdList);
        projectDto.setUserList(userDtoList);

        return projectDto;
    }

    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }
}
