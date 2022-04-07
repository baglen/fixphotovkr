package ru.arlabs.taskservice.security.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.attempts.AttemptService;
import ru.arlabs.taskservice.security.AuthenticationService;
import ru.arlabs.taskservice.security.exception.AuthorizationException;
import ru.arlabs.taskservice.security.request.AuthInfo;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.util.HttpUtil;
import ru.arlabs.taskservice.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeb
 */
@Service
public class JwtAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final HttpUtil httpUtil;
    private final UserDetailsService userDetailsService;
    private final AttemptService loginAttemptService;

    @Autowired
    public JwtAuthenticationService(AuthenticationManager authenticationManager,
                                    JwtUtil jwtTokenUtil,
                                    HttpUtil httpUtil,
                                    UserDetailsService userDetailsService,
                                    @Qualifier("loginAttemptService") AttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.httpUtil = httpUtil;
        this.userDetailsService = userDetailsService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public String authenticate(HttpServletRequest request, AuthInfo authenticationRequest) throws AuthorizationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(),
                authenticationRequest.getPassword());
        String ip = httpUtil.getClientIP(request);
        loginAttemptService.attempt(ip);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException | LockedException e ) {
            throw new AuthorizationException("Invalid login or password");
        }
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());
        loginAttemptService.invalidate(ip);
        return jwtTokenUtil.generateToken((User) userDetails);
    }
}
