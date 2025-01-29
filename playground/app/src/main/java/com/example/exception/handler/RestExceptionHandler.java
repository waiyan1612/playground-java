package com.example.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Order(Integer.MIN_VALUE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    // Used by ProblemDetail.type (https://datatracker.ietf.org/doc/html/rfc9457).
    // Recommended (but not required) that it be a URL that provides human-readable documentation about the problem.
    private static final URI PROBLEM_DETAIL_NOT_FOUND = URI.create("https://example.com/docs/errors/not_found");
    private static final URI PROBLEM_DETAIL_FORBIDDEN = URI.create("https://example.com/docs/errors/forbidden");
    private static final URI PROBLEM_DETAIL_INTERNAL = URI.create("https://example.com/docs/errors/server_error");
    private static final URI PROBLEM_DETAIL_NOT_IMPLEMENTED = URI.create("https://example.com/docs/errors/coming_soon");

    @ExceptionHandler({org.springframework.security.access.AccessDeniedException.class})
    protected ProblemDetail handle403(Exception exception, WebRequest webRequest) {
        LOGGER.warn("{} is forbidden to access {}",
                Objects.requireNonNull(webRequest.getUserPrincipal()).getName(),
                ((ServletWebRequest)webRequest).getRequest().getRequestURI()
        );
        return generateProblemDetail(FORBIDDEN, "", webRequest);
    }

    @ExceptionHandler({jakarta.persistence.EntityNotFoundException.class, com.example.exception.NotFoundException.class})
    protected ProblemDetail handle404(Exception exception, WebRequest webRequest) {
        LOGGER.warn(exception.getMessage(), exception);
        return generateProblemDetail(NOT_FOUND, exception.getMessage(), webRequest);
    }

    @ExceptionHandler({Exception.class})
    protected ProblemDetail handle500(Exception exception, WebRequest webRequest) {
        LOGGER.error(exception.getMessage(), exception);
        return generateProblemDetail(INTERNAL_SERVER_ERROR, exception.getMessage(), webRequest);
    }

    @ExceptionHandler({com.example.exception.NotImplementedException.class})
    protected ProblemDetail handle501(Exception exception, WebRequest webRequest) {
        LOGGER.error(exception.getMessage(), exception);
        return generateProblemDetail(NOT_IMPLEMENTED, exception.getMessage(), webRequest);
    }

    private ProblemDetail generateProblemDetail(HttpStatus status, String detail, WebRequest webRequest) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        switch(status) {
            case NOT_FOUND -> problem.setType(PROBLEM_DETAIL_NOT_FOUND);
            case FORBIDDEN -> problem.setType(PROBLEM_DETAIL_FORBIDDEN);
            case INTERNAL_SERVER_ERROR -> problem.setType(PROBLEM_DETAIL_INTERNAL);
            case NOT_IMPLEMENTED -> problem.setType(PROBLEM_DETAIL_NOT_IMPLEMENTED);
            default -> LOGGER.debug("No URI found for {}", status);
        }
        problem.setInstance(URI.create(((ServletWebRequest)webRequest).getRequest().getRequestURI()));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
