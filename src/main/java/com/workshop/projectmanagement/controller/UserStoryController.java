package com.workshop.projectmanagement.controller;

import com.workshop.projectmanagement.service.UserStoryService;
import com.workshop.projectmanagement.dto.UserStoryDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-story")
public class UserStoryController {
    private UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService){
        this.userStoryService = userStoryService;
    }

    @PostMapping
    public UserStoryDto createUserStory(@RequestBody UserStoryDto UserStoryDto){
        return userStoryService.create(UserStoryDto);
    }

    @PutMapping
    public UserStoryDto updateUserStory(@RequestBody UserStoryDto UserStoryDto){
        return userStoryService.update(UserStoryDto);
    }

    @GetMapping ("/{id}")
    public UserStoryDto getUserStory(@PathVariable Integer id){
        return userStoryService.get(id);
    }

    @DeleteMapping ("/{id}")
    public void deleteUserStory(@PathVariable Integer id){
        userStoryService.delete(id);
    }

}
