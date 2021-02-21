package com.springboot.springmongooauthjwt.exception;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.exception.ExceptionDetails;
import com.springboot.springmongooauthjwt.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    // handling unauthorized access exception
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> UnauthorizedExceptionHandling(UnauthorizedException exception, WebRequest request){

        ExceptionDetails errorDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    // handling resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request){

        ExceptionDetails errorDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handling bad request exception
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestHandling(BadRequestException exception, WebRequest request){

        ExceptionDetails errorDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    // handling global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){

        ExceptionDetails errorDetails =
                new ExceptionDetails(new Date(), exception.getMessage(), HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.toString(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

}
