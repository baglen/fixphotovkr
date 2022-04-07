package ru.arlabs.taskservice.security.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.arlabs.taskservice.security.converter.SecurityConverter;
import ru.arlabs.taskservice.security.exception.TokenExpiredException;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Jeb
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final SecurityConverter securityConverter;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil, SecurityConverter securityConverter) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.securityConverter = securityConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        Claims claims = null;

        String jwtToken;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);

            Optional<Claims> claimsOptional = jwtUtil.parseToken(jwtToken);

            if (claimsOptional.isPresent()) {
                claims = claimsOptional.get();
            }


        }


        if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(claims.getSubject());

            if (jwtUtil.validateToken(claims, (User) userDetails)) {

                if (jwtUtil.isTokenExpired(claims)) {
                    throw new TokenExpiredException("Token expired");
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

                        securityConverter.convertUser((User) userDetails), null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken

                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }

        chain.doFilter(request, response);

    }

}
