package com.example.springboothomework.configuration;

import com.example.springboothomework.exception.UnauthorizedException;
import com.example.springboothomework.service.UserDetailService;
import com.example.springboothomework.util.JwtUtil;
import jakarta.servlet.FilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final String HEALTH_CHECK_PATH = "/health";
    private final String LOGIN_PATH = "/login";
    private final String AUTH_PATH = "/auth/**";
    private final List<String> ALLOWED_PATHS = List.of(HEALTH_CHECK_PATH, LOGIN_PATH, AUTH_PATH);
    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;
    private static final PathMatcher MATCHER = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Unauthorized");
            }

            // Extract raw JWT (without the "Bearer " prefix) and trim any extra whitespace
            final String jwt = authHeader.substring(7).trim();

            // Load user and validate token
            final String username = jwtUtil.getUsernameFromToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = userDetailService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new UnauthorizedException("Invalid or expired token");
                }
            }

            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            writeUnauthorizedResponse(response, ex.getMessage());
        } catch (Exception ex) {
            writeUnauthorizedResponse(response, "Unauthorized");
        }
    }

    private void writeUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }

    @Override
    // Executes before doFilterInternal
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Check if the request path matches any of the allowed paths
        return ALLOWED_PATHS.stream().anyMatch(pattern -> MATCHER.match(pattern, request.getRequestURI()));
    }
}
