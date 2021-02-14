package org.bitbucket.draganbjedov.project.manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "PM_SECRET_KEY_FOR_JWT";
    public static final String PREFIX = "Bearer ";
    private static final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 minutes

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
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validate(final String token) {
        Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        return true;
    }

    public long getUserId(final String token) {
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong((String) claims.get("id"));
    }
}
