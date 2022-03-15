package com.workshop.projectmanagement.controller;

import com.workshop.projectmanagement.service.UserStoryService;
import com.workshop.projectmanagement.dto.UserStoryDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-story")
public class UserStoryController {
    private UserStoryService UserStoryService;

    public UserStoryController(UserStoryService UserStoryService){
        this.UserStoryService = UserStoryService;
    }

    @PostMapping
    public UserStoryDto createUserStory(@RequestBody UserStoryDto UserStoryDto){
        return UserStoryService.create(UserStoryDto);
    }

    @PutMapping
    public UserStoryDto updateUserStory(@RequestBody UserStoryDto UserStoryDto){
        return UserStoryService.update(UserStoryDto);
    }

    @GetMapping ("/{id}")
    public UserStoryDto getUserStory(@PathVariable Integer id){
        return UserStoryService.get(id);
    }

    @DeleteMapping ("/{id}")
    public void deleteUserStory(@PathVariable Integer id){
        UserStoryService.delete(id);
    }

}
