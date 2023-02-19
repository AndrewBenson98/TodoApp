package com.arbenson.taskapi.service;

import com.arbenson.taskapi.model.Task;
import com.arbenson.taskapi.repository.TaskRepository;
import com.arbenson.taskapi.util.exceptions.TaskNotFoundException;
import com.arbenson.taskapi.util.exceptions.UserNotFoundException;
import com.arbenson.taskapi.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;
    private JWTUtil jwtUtil;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, JWTUtil jwtUtil) {
        this.taskRepository = taskRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Gets username from jwt token and returns list of all tasks associated with that username
     * @param token
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public List<Task> getTasksByUsername(String token)  {

        String username =jwtUtil.getTokenSubject(token);
        List<Task> tasksList = taskRepository.findByUsername(username);
        return tasksList;
    }


    /**
     * Gets username from jwt token and creates a new task
     * @param task
     * @param token
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public Task createTask(Task task, String token){

        String username =jwtUtil.getTokenSubject(token);
        task.setCompleted(false);
        task.setUsername(username);
        return taskRepository.save(task);
    }


    /**
     * Gets a single task
     * @param taskId
     * @param token
     * @return
     * @throws UserNotFoundException
     * @throws TaskNotFoundException
     */
    @Override
    public Task getTask(long taskId, String token) throws TaskNotFoundException {

        //Get the username from the jwt token
        String username =jwtUtil.getTokenSubject(token);

        //Get the task from the databse
        Optional<Task> optTask = taskRepository.findById(taskId);

        //Check that the task exists
        if(optTask.isPresent()){

            Task task = optTask.get();

            //check if this task belongs to this user
            if(username.equals(task.getUsername())){
                return task;
            }else{
                throw new TaskNotFoundException("This task does not belong to this user");
            }

        }else{
            throw new TaskNotFoundException("Task not found");
        }

    }

    /**
     * Toggle a task between complete/not complete (true/ false)
     * @param taskId
     * @param token
     * @return
     * @throws UserNotFoundException
     * @throws TaskNotFoundException
     */
    @Override
    public Task toggleTaskCompletion(long taskId, String token) throws  TaskNotFoundException {

        //get username from jwt token
        String username =jwtUtil.getTokenSubject(token);

        //Get the task from the database
        Optional<Task> optTask = taskRepository.findById(taskId);

        //Check that the task exists
        if(optTask.isPresent()){

            Task task = optTask.get();

            //check if this task belongs to this user
            if(username.equals(task.getUsername())){

                //update task and save
                task.setCompleted(!task.isCompleted());
                taskRepository.save(task);
                return task;

            }else{
                throw new TaskNotFoundException("This task does not belong to this user");
            }

        }else{
            throw new TaskNotFoundException("Task not found");
        }
    }


    /**
     * Deletes a task
     * @param taskId
     * @param token
     * @throws UserNotFoundException
     * @throws TaskNotFoundException
     */
    @Override
    public void deleteTask(long taskId, String token) throws TaskNotFoundException {

        //get username from jwt token
        String username =jwtUtil.getTokenSubject(token);

        //Get the task from the database
        Optional<Task> optTask = taskRepository.findById(taskId);

        //Check if the task exists
        if(optTask.isPresent()){

            Task task = optTask.get();

            //check if this task belongs to this user
            if(username.equals(task.getUsername())){
                //Delete the task
                taskRepository.deleteById(taskId);
            }else{
                throw new TaskNotFoundException("This task does not belong to this user");
            }

        }else{
            throw new TaskNotFoundException("Task not found");
        }

    }
}
