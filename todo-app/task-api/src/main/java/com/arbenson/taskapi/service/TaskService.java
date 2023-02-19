package com.arbenson.taskapi.service;

import com.arbenson.taskapi.model.Task;
import com.arbenson.taskapi.util.exceptions.TaskNotFoundException;
import com.arbenson.taskapi.util.exceptions.UserNotFoundException;

import java.util.List;

public interface TaskService {

    public List<Task> getTasksByUsername(String token) ;
    public Task createTask(Task task, String token);
    public Task getTask(long taskId, String token) throws TaskNotFoundException;
    public Task toggleTaskCompletion(long taskId, String token)throws  TaskNotFoundException;
    public void deleteTask(long taskId, String token)throws  TaskNotFoundException;




}
