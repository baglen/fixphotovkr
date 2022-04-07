package ru.arlabs.taskservice.attempts;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeb
 */
public interface AttemptService {
    void attempt(String key);

    void attempt(HttpServletRequest request);

    void invalidate(HttpServletRequest request);

    void invalidate(String key);
}
