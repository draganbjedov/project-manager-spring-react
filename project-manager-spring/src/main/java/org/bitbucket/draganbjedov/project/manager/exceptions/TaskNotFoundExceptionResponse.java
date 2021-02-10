package org.bitbucket.draganbjedov.project.manager.exceptions;

import lombok.Data;

@Data
public class TaskNotFoundExceptionResponse {
    private final String projectSequence;
}
