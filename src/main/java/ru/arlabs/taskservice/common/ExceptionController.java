package ru.arlabs.taskservice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.arlabs.taskservice.attempts.exception.TooManyAttemptsException;
import ru.arlabs.taskservice.util.ControllerUtil;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeb
 */
@RestControllerAdvice
public class ExceptionController {
    private final ControllerUtil controllerUtil;


    @Autowired
    public ExceptionController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @ExceptionHandler(TooManyAttemptsException.class)
    public ResponseEntity<?> tooManyRequestHandler(HttpServletRequest request, TooManyAttemptsException exception) {
        var status = HttpStatus.TOO_MANY_REQUESTS;
        return ResponseEntity.status(status)
                .header("Retry-After", String.valueOf(exception.getRetryAfter()))
                .header("X-RateLimit-Limit", String.valueOf(exception.getRateLimit()))
                .header("X-RateLimit-Remaining", String.valueOf(exception.getRateRemaining()))
                .body(controllerUtil.getError(request, status, "Too many requests").getBody());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(HttpServletRequest request, MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message;
        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        } else {
            ObjectError globalError = exception.getBindingResult().getGlobalError();
            message = globalError.getDefaultMessage();
        }
        return controllerUtil.getError(request, status, message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(HttpServletRequest request, EntityNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return controllerUtil.getError(request, status, "Entity not found");
    }

}
