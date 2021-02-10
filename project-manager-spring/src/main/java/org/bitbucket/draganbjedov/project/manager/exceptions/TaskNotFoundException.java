package org.bitbucket.draganbjedov.project.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String projectSequence) {
        super("Task with project sequence '" + projectSequence + "' not found");
    }

    public TaskNotFoundException(String projectIdentifier, String projectSequence) {
        super("Task with project sequence '" + projectSequence + "' not found in project '" + projectIdentifier + "'");
    }
}
