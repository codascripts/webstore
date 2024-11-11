package dev.codascripts.productservice.exceptions;

import dev.codascripts.productservice.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/* global exception handling class */
// instead of writing the same error everywhere we create a class
// and write all the different types of error and when there is an error in some method we annotate which error to throw
@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        System.out.println("Not found exception occurred");
        return new ResponseEntity(
                new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }
}
