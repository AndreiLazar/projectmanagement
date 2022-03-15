package com.workshop.projectmanagement.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.workshop.projectmanagement.dto.UserDto;
import com.workshop.projectmanagement.dto.UserStoryDto;
import com.workshop.projectmanagement.entity.UserStoryEntity;
import com.workshop.projectmanagement.repo.UserStoryRepository;
import org.springframework.stereotype.Service;

@Service
public class UserStoryService {
    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private UserStoryRepository userStoryRepository;
    private UserService userService;

    public UserStoryService(UserStoryRepository userStoryRepository, UserService userService){
        this.userStoryRepository = userStoryRepository;
        this.userService = userService;
    }

    public UserStoryDto create(UserStoryDto userStoryDto){
        UserStoryEntity userStoryEntity = mapper.map(userStoryDto, UserStoryEntity.class);
        UserStoryEntity savedUserStory = userStoryRepository.save(userStoryEntity);

        UserStoryDto savedUserStoryDto = mapper.map(savedUserStory, UserStoryDto.class);
        Integer userId = savedUserStoryDto.getUser().getId();
        if(null==userId){
            return userStoryDto;
        }

        UserDto userDto = userService.getByUserId(userId);
        savedUserStoryDto.setUser(userDto);

        return savedUserStoryDto;
    }

    public UserStoryDto update(UserStoryDto userStoryDto){
        UserStoryEntity userStoryEntity = mapper.map(userStoryDto, UserStoryEntity.class);
        UserStoryEntity savedUserStory = userStoryRepository.save(userStoryEntity);

        return mapper.map(savedUserStory, UserStoryDto.class);
    }

    public UserStoryDto get(Integer id){
        UserStoryEntity userStoryEntity = userStoryRepository.getById(id);
        UserStoryDto userStoryDto = mapper.map(userStoryEntity, UserStoryDto.class);

        Integer userId = userStoryDto.getUser().getId();
        if(null==userId){
            return userStoryDto;
        }

        UserDto userDto = userService.getByUserId(userId);
        userStoryDto.setUser(userDto);

        return userStoryDto;
    }

    public void delete(Integer id){
        userStoryRepository.deleteById(id);
    }
}
