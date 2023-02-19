package com.arbenson.taskapi.controller;

import com.arbenson.taskapi.model.Task;
import com.arbenson.taskapi.service.TaskService;
import com.arbenson.taskapi.util.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {


    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasksByUsername(@RequestHeader("Authorization") String token){

        try{

            List<Task> taskList = taskService.getTasksByUsername(token);
//            Thread.sleep(3000);
            return new ResponseEntity<List<Task>>(taskList, HttpStatus.OK);


        }catch(UserNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
//        catch(Exception e){
//            return new ResponseEntity<String>("Timed out ", HttpStatus.NOT_FOUND);
//        }

    }



    @PostMapping("/tasks")
    public ResponseEntity<?> createNewTask(@RequestBody Task newTask, @RequestHeader("Authorization") String token){

        try{

            Task task = taskService.createTask(newTask,token);
            return new ResponseEntity<Task>(task, HttpStatus.OK);


        }catch(UserNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    //update task
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<?> toggleTaskCompletion(@PathVariable("taskId") long taskId, @RequestHeader("Authorization") String token){

        try{

            Task task = taskService.toggleTaskCompletion(taskId,token);
            return new ResponseEntity<Task>(task, HttpStatus.OK);


        }catch(UserNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //delete task
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") long taskId, @RequestHeader("Authorization") String token){
        try{

            taskService.deleteTask(taskId,token);
            return new ResponseEntity<String>("Task has been deleted", HttpStatus.OK);


        }catch(UserNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }





}
