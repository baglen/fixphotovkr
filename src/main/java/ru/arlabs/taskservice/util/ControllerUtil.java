package ru.arlabs.taskservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Jeb
 */
@Component
public class ControllerUtil {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ControllerUtil(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public ResponseEntity<Object> getError(HttpServletRequest request, HttpStatus status, String message) {
        WebRequest webRequest = new ServletWebRequest(request);
        return getError(webRequest, status, message);
    }

    public ResponseEntity<Object> getError(WebRequest request, HttpStatus status, String message) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        errorAttributes.put("message", message);
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("status", status.value());
        return ResponseEntity.status(status)
                .body(errorAttributes);
    }
}
