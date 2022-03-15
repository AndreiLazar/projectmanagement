package com.workshop.projectmanagement.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.workshop.projectmanagement.dto.UserDto;
import com.workshop.projectmanagement.dto.TaskDto;
import com.workshop.projectmanagement.entity.TaskEntity;
import com.workshop.projectmanagement.entity.TaskEntity;
import com.workshop.projectmanagement.repo.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService){
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public TaskDto create(TaskDto taskDto){
        TaskEntity taskEntity = mapper.map(taskDto, TaskEntity.class);
        TaskEntity savedUserStory = taskRepository.save(taskEntity);

        TaskDto savedUserStoryDto = mapper.map(savedUserStory, TaskDto.class);
        Integer userId = savedUserStoryDto.getUser().getId();
        if(null==userId){
            return taskDto;
        }

        UserDto userDto = userService.getByUserId(userId);
        savedUserStoryDto.setUser(userDto);

        return savedUserStoryDto;
    }

    public TaskDto update(TaskDto taskDto){
        TaskEntity taskEntity = mapper.map(taskDto, TaskEntity.class);
        TaskEntity savedUserStory = taskRepository.save(taskEntity);

        return mapper.map(savedUserStory, TaskDto.class);
    }

    public TaskDto get(Integer id){
        TaskEntity taskEntity = taskRepository.getById(id);
        TaskDto taskDto = mapper.map(taskEntity, TaskDto.class);

        Integer userId = taskDto.getUser().getId();
        if(null==userId){
            return taskDto;
        }

        UserDto userDto = userService.getByUserId(userId);
        taskDto.setUser(userDto);

        return taskDto;
    }

    public void delete(Integer id){
        taskRepository.deleteById(id);
    }
}
