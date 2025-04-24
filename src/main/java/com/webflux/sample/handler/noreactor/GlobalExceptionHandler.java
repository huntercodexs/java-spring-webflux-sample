package com.webflux.sample.handler.noreactor;

import com.webflux.sample.handler.noreactor.exception.BadRequestException;
import com.webflux.sample.handler.noreactor.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponseException> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new CustomResponseException(ex.getMessage(), ex.getCode(), ex.getTrack()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponseException> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new CustomResponseException(ex.getMessage(), ex.getCode(), ex.getTrack()));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("An unexpected error occurred: " + ex.getMessage());
//    }
}
