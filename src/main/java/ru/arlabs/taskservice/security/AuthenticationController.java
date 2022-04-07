package ru.arlabs.taskservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.arlabs.taskservice.common.exception.ObjectAlreadyExistException;
import ru.arlabs.taskservice.security.exception.AuthorizationException;
import ru.arlabs.taskservice.security.request.*;
import ru.arlabs.taskservice.security.response.AuthResult;
import ru.arlabs.taskservice.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VerifyService verifyService;
    private final UserService userService;
    private final RestoreService restoreService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    VerifyService verifyService,
                                    UserService userService,
                                    RestoreService restoreService) {
        this.authenticationService = authenticationService;
        this.verifyService = verifyService;
        this.userService = userService;
        this.restoreService = restoreService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterInfo request) throws ObjectAlreadyExistException {
        userService.register(request);
    }

    @PostMapping("/verify")
    public void register(@Valid @RequestBody VerificationInfo request) {
        verifyService.verify(request);
    }


    @PostMapping("/reset")
    public void sendRestoreMessage(HttpServletRequest servletRequest, @Valid @RequestBody ResetInfo request) {
        restoreService.restore(servletRequest, request.getEmail());
    }

    @PostMapping("/change")
    public void changePassword(@Valid @RequestBody ResetPasswordInfo request) {
        restoreService.changePassword(request);
    }

    @PostMapping("/login")
    public AuthResult createAuthenticationToken(HttpServletRequest servletRequest,
                                                @RequestBody AuthInfo authRequest) throws AuthorizationException {
        return new AuthResult(authenticationService.authenticate(servletRequest, authRequest));
    }
}
