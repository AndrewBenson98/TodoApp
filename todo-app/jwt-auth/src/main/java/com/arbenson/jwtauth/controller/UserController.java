package com.arbenson.jwtauth.controller;

import com.arbenson.jwtauth.model.User;
import com.arbenson.jwtauth.service.UserService;
import com.arbenson.jwtauth.util.exception.InvalidUsernameAndPasswordException;
import com.arbenson.jwtauth.util.exception.UserAlreadyExistsException;
import com.arbenson.jwtauth.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    /**
     * Saves new user into the database.
     * Then return JWT
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){

        try{
            Map<String, String> token = userService.register(user);
            return new ResponseEntity<Map<String, String>>(token, HttpStatus.OK);
        }catch(UserAlreadyExistsException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }catch(InvalidUsernameAndPasswordException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Checks if username and password exist in the database.
     * Then return JWT
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        try{
            Map<String, String> token = userService.login(user);
            return new ResponseEntity<Map<String, String>>(token, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(InvalidUsernameAndPasswordException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/message")
    public ResponseEntity<?> message(){
        return new ResponseEntity<String>("Hello. This is jwt auth module", HttpStatus.OK);
    }

}
