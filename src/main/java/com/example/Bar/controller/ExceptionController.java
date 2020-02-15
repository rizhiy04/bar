package com.example.Bar.controller;

import com.example.Bar.dto.ErrorMessage;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.exception.SuchUserAlreadyExistException;
import com.example.Bar.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.UnexpectedTypeException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NoSuchElementException.class, SuchUserAlreadyExistException.class, WrongPasswordException.class,
            UsernameNotFoundException.class, MethodArgumentNotValidException.class, UnexpectedTypeException.class})
    private ResponseEntity<ErrorMessage> handleBadRequest(final Exception e){
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
