package com.workshop.projectmanagement.controller;

import com.workshop.projectmanagement.dto.TaskDto;
import com.workshop.projectmanagement.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto){
        return taskService.create(taskDto);
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return taskService.update(taskDto);
    }

    @GetMapping ("/{id}")
    public TaskDto getTask(@PathVariable Integer id){
        return taskService.get(id);
    }

    @DeleteMapping ("/{id}")
    public void deleteUTask(@PathVariable Integer id){
        taskService.delete(id);
    }

}
