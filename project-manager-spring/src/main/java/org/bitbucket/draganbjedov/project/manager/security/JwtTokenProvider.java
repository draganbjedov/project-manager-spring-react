package org.bitbucket.draganbjedov.project.manager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.bitbucket.draganbjedov.project.manager.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "PM_SECRET_KEY_FOR_JWT";
    private static final String PREFIX = "Bearer ";

    public String generate(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        String userId = String.valueOf(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return PREFIX + Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
