package org.bitbucket.draganbjedov.project.manager.exceptions;

import lombok.Data;

@Data
public class InvalidLoginResponse {
    private final String username = "Invalid username";
    private final String password = "Invalid password";
}
