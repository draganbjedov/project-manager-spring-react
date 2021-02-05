package org.bitbucket.draganbjedov.project.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<ProjectIdentifierExceptionResponse> handleProjectIdentifierException(ProjectIdentifierException ex) {
        ProjectIdentifierExceptionResponse exResponse = new ProjectIdentifierExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exResponse, HttpStatus.BAD_REQUEST);
    }
}
