package com.arbenson.jwtauth.service;

import com.arbenson.jwtauth.model.User;
import com.arbenson.jwtauth.repository.UserRepository;
import com.arbenson.jwtauth.util.exception.InvalidUsernameAndPasswordException;
import com.arbenson.jwtauth.util.exception.UserAlreadyExistsException;
import com.arbenson.jwtauth.util.exception.UserNotFoundException;
import com.arbenson.jwtauth.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JWTUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder=passwordEncoder;
    }

    /**
     *
     * @param newUser
     * @return
     * @throws UserAlreadyExistsException
     * @throws InvalidUsernameAndPasswordException
     */
    @Override
    public Map<String, String> register(User newUser) throws UserAlreadyExistsException, InvalidUsernameAndPasswordException {

        Optional<User> optUser = userRepository.findByUsername(newUser.getUsername());

        //Check that the user does not already exist
        if(optUser.isEmpty()){

            //Check that username and password aren't blank or null
            if(newUser.getUsername()!=null && newUser.getPassword()!=null &&
                    !newUser.getUsername().isBlank() && !newUser.getPassword().isBlank()){

                //Encrypt password and save the new user
                String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
                newUser.setPassword(encryptedPassword);
                User user = userRepository.save(newUser);

                //generate jwt and return it
                String token = jwtUtil.generateToken(user);
                return Collections.singletonMap("token", token);
            }else{
                //Throw exception if username or password is blank
                throw new InvalidUsernameAndPasswordException("Invalid Username or Password");
            }

        }else{
            //Throw error if that username already exists
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    /**
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws InvalidUsernameAndPasswordException
     */
    @Override
    public Map<String, String> login(User user) throws UserNotFoundException, InvalidUsernameAndPasswordException{

        Optional<User> optUser = userRepository.findByUsername(user.getUsername());

        //Check that the username exists
        if(optUser.isPresent()){

            User dbUser = optUser.get();

            //Check if passwords match
            if(passwordEncoder.matches(user.getPassword(),dbUser.getPassword())){
                //generate jwt and return it
                String token = jwtUtil.generateToken(user);
                return Collections.singletonMap("token", token);
            }else{
                //Throw exception if username or password is blank
                throw new InvalidUsernameAndPasswordException("Invalid Username or Password");
            }

        }else{
            //Throw error if that username already exists
            throw new UserNotFoundException("User does not exist");
        }

    }
}
