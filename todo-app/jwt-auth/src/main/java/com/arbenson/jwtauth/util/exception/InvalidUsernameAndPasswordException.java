package com.arbenson.jwtauth.util.exception;

public class InvalidUsernameAndPasswordException extends RuntimeException{
    public InvalidUsernameAndPasswordException(String msg){
        super(msg);
    }
}
