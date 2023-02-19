package com.arbenson.jwtauth.service;

import com.arbenson.jwtauth.model.User;
import com.arbenson.jwtauth.util.exception.InvalidUsernameAndPasswordException;
import com.arbenson.jwtauth.util.exception.UserAlreadyExistsException;
import com.arbenson.jwtauth.util.exception.UserNotFoundException;

import java.util.Map;

public interface UserService {

    public Map<String, String> register(User user) throws UserAlreadyExistsException, InvalidUsernameAndPasswordException;
    public Map<String, String> login(User user) throws UserNotFoundException, InvalidUsernameAndPasswordException;


}
