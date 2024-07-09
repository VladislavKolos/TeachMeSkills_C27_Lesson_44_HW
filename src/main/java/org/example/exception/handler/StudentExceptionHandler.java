package org.example.exception.handler;

import jakarta.servlet.ServletException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * Exception handler for controllers associated with operations on students.
 * Handles various types of exceptions and redirects to appropriate error pages.
 */
@ControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Exception e) {
        return "runtimeExceptionError";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(Exception e) {
        return "nullPointerExceptionError";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(Exception e) {
        return "ioExceptionError";
    }

    @ExceptionHandler(ServletException.class)
    public String handleServletException(Exception e) {
        return "servletExceptionError";
    }

    @ExceptionHandler(Exception.class)
    public String handleAnotherException(Exception e) {
        return "anotherExceptionError";
    }
}
