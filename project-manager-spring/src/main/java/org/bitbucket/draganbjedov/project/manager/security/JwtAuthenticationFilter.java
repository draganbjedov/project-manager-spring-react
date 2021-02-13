package org.bitbucket.draganbjedov.project.manager.security;

import org.bitbucket.draganbjedov.project.manager.domain.User;
import org.bitbucket.draganbjedov.project.manager.services.ProjectManagerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ProjectManagerUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            final String token = extractToken(httpServletRequest);
            if (token != null && tokenProvider.validate(token)) {
                long userId = tokenProvider.getUserId(token);
                User user = userDetailService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Authentication failed", ex);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtTokenProvider.PREFIX)) {
            return bearerToken.substring(JwtTokenProvider.PREFIX.length());
        }
        return null;
    }
}
