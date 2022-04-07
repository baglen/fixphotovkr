package ru.arlabs.taskservice.attempts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jeb
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class TooManyAttemptsException extends RuntimeException {
    private final long retryAfter;
    private final long rateLimit;
    private final long rateRemaining;

    public TooManyAttemptsException(long retryAfter, long rateLimit, long rateRemaining) {
        this.retryAfter = retryAfter;
        this.rateLimit = rateLimit;
        this.rateRemaining = rateRemaining;
    }

    public long getRetryAfter() {
        return retryAfter;
    }

    public long getRateLimit() {
        return rateLimit;
    }

    public long getRateRemaining() {
        return rateRemaining;
    }
}
