package com.workshop.projectmanagement.service;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.workshop.projectmanagement.dto.ProjectDto;
import com.workshop.projectmanagement.dto.ProjectPatchNameDto;
import com.workshop.projectmanagement.dto.UserDto;
import com.workshop.projectmanagement.entity.ProjectEntity;
import com.workshop.projectmanagement.entity.UserEntity;
import com.workshop.projectmanagement.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Value("${usermanagement.user.getAllByIds}")
    String usermanagementGetAllByIdsUrl;
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private ProjectRepository projectRepository;

    private RestTemplate restTemplate;

    public ProjectService(ProjectRepository projectRepository,
                          RestTemplate restTemplate,
                          @Value("${usermanagement.user.getAllByIds}")
                                  String usermanagementGetAllByIdsUrl){
        this.projectRepository = projectRepository;
        this.restTemplate = restTemplate;
        this.usermanagementGetAllByIdsUrl = usermanagementGetAllByIdsUrl;
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

    public ProjectDto getProject(Integer id){
        ProjectEntity projectEntity = projectRepository.getById(id);
        String userList = projectEntity.getUserList()
                .stream()
                .map(userEntity -> userEntity.getId().toString())
                .collect(Collectors.joining(","));


        UserDto[] userDtos =  restTemplate.getForObject(usermanagementGetAllByIdsUrl + "/" + userList, UserDto[].class);
        ProjectDto projectDto = mapper.map(projectEntity, ProjectDto.class);
        projectDto.setUserList(Arrays.asList(userDtos));

        return projectDto;
    }

    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }
}
