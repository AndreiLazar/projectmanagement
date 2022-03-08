package com.workshop.projectmanagement.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.workshop.projectmanagement.dto.ProjectDto;
import com.workshop.projectmanagement.dto.ProjectPatchNameDto;
import com.workshop.projectmanagement.dto.UserDto;
import com.workshop.projectmanagement.entity.ProjectEntity;
import com.workshop.projectmanagement.repo.ProjectRepository;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @Mock
    ProjectRepository projectRepositoryMock;
    @Mock
    private UserService userService;

    ProjectService projectService;

    @BeforeEach
    public void beforeEach(){
        projectService = new ProjectService(projectRepositoryMock, userService);
    }

    private ProjectDto generateProjectDto(){
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("newName");
        projectDto.setDescription("description");

        UserDto userDto = new UserDto();
        userDto.setId(1);

        projectDto.setUserList(Collections.singletonList(userDto));

        return projectDto;
    }

    @Test
    void createProject() {
        //given
        ProjectDto projectDto = generateProjectDto();

        ProjectEntity projectEntity = mapper.map(projectDto, ProjectEntity.class);
        Mockito.when(projectRepositoryMock.save(any()))
                .thenReturn(projectEntity);

        List<Integer> userIdList = projectDto.getUserList()
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        Mockito.when(userService.getByUserIdList(userIdList)).thenReturn(projectDto.getUserList());

        //when
        ProjectDto savedProject = projectService.createProject(projectDto);

        //then
        assertEquals(projectDto.getName(), savedProject.getName());
        assertEquals(projectDto.getDescription(), savedProject.getDescription());
        assertEquals(projectDto.getName(), savedProject.getName());

        UserDto saveUser = savedProject.getUserList().get(0);
        assertEquals(projectDto.getUserList().get(0).getId(), saveUser.getId());
    }

    @Test
    void updateProject() {
        //given
        ProjectDto projectDto = generateProjectDto();

        ProjectEntity projectEntity = mapper.map(projectDto, ProjectEntity.class);
        Mockito.when(projectRepositoryMock.save(any()))
                .thenReturn(projectEntity);

        List<Integer> userIdList = projectDto.getUserList()
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        Mockito.when(userService.getByUserIdList(userIdList)).thenReturn(projectDto.getUserList());

        //when
        ProjectDto savedProject = projectService.updateProject(projectDto);

        //then
        assertEquals(projectDto.getName(), savedProject.getName());
        assertEquals(projectDto.getDescription(), savedProject.getDescription());
        assertEquals(projectDto.getName(), savedProject.getName());

        UserDto saveUser = savedProject.getUserList().get(0);
        assertEquals(projectDto.getUserList().get(0).getId(), saveUser.getId());
    }

    @Test
    void patchProject() {
        //given
        ProjectDto projectDto = generateProjectDto();
        ProjectPatchNameDto projectPatchNameDto = new ProjectPatchNameDto();
        projectPatchNameDto.setId(1);
        projectPatchNameDto.setName("patchedName");

        ProjectEntity projectEntity = mapper.map(projectPatchNameDto, ProjectEntity.class);
        Mockito.when(projectRepositoryMock.getById(projectPatchNameDto.getId()))
                .thenReturn(projectEntity);

        Mockito.when(projectRepositoryMock.save(any())).thenReturn(projectEntity);

        //when
        ProjectDto patchedProject = projectService.patchProject(projectPatchNameDto);

        //then
        assertEquals(projectPatchNameDto.getName(), patchedProject.getName());
     }

    @Test
    void getProject() {
        //given
        ProjectDto projectDto = generateProjectDto();
        projectDto.setId(1);

        ProjectEntity projectEntity = mapper.map(projectDto, ProjectEntity.class);
        Mockito.when(projectRepositoryMock.getById(projectDto.getId()))
                .thenReturn(projectEntity);

        List<Integer> userIdList = projectDto.getUserList()
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        Mockito.when(userService.getByUserIdList(userIdList)).thenReturn(projectDto.getUserList());

        //when
        ProjectDto getProject = projectService.getProject(1);

        //then
        assertEquals(projectDto.getName(), getProject.getName());
        assertEquals(projectDto.getDescription(), getProject.getDescription());
        assertEquals(projectDto.getName(), getProject.getName());

        UserDto getUser = getProject.getUserList().get(0);
        assertEquals(projectDto.getUserList().get(0).getId(), getUser.getId());
    }

    @Test
    void deleteProject() {
        //given
        //when
        projectService.deleteProject(1);
        //then
    }
}