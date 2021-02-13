package org.bitbucket.draganbjedov.project.manager.payload;

import lombok.Data;

@Data
public class JwtLoginResponse {
    private final boolean success;
    private final String token;
}
