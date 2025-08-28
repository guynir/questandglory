package com.questandglory.web.controllers;

import com.questandglory.ApplicationException;
import com.questandglory.services.UnknownGamePlayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ControllerExceptionHandler {

    public record ErrorResponse(String message) {
    }

    /**
     * Handle all "not-found" exceptions in one place.
     *
     * @param ex Exception to handle.
     */
    @ExceptionHandler(value = {UnknownGamePlayException.class, UnknownGamePlayException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUnknownGamePlayException(ApplicationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

}
