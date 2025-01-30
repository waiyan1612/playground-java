package com.example.playground.base.aspect;

import com.example.playground.base.model.AuditRequest;
import com.example.playground.base.model.AuditResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Streams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuditAspect {

    // Refs:
    // https://medium.com/@AlexanderObregon/how-to-create-an-audit-trail-in-spring-boot-applications-3ecacd362825
    // https://stackoverflow.com/questions/71916917/spring-aop-for-controller-inside-package

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);
    private final ObjectWriter objectWriter;

    @Autowired
    public AuditAspect(ObjectMapper objectMapper) {
        this.objectWriter = objectMapper.writer();
    }

    @Pointcut("within(com.example.playground..*) && bean(*Controller))")
    public void restController() {
    }

//    @Before("restController()")
    public AuditRequest logRequest(JoinPoint jp) {

        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String requestBody = getRequestBody(jp);

        AuditRequest auditRequest = new AuditRequest(
                request.getUserPrincipal().getName(),
                request.getMethod(),
                request.getRequestURI(),
                requestBody,
                Instant.now().toEpochMilli()
        );
        log.info("{}", auditRequest);
        return auditRequest;
    }

    @Around("restController()")
    public Object logRequestResponse(ProceedingJoinPoint jp) throws Throwable {

        AuditRequest auditRequest = logRequest(jp);

        long startTime = System.currentTimeMillis();
        Object response = jp.proceed();
        long endTime = System.currentTimeMillis();

        HttpServletResponse httpResponse = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        AuditResponse auditResponse = new AuditResponse(
                auditRequest.user(),
                auditRequest.op(),
                auditRequest.URI(),
                httpResponse != null ? httpResponse.getStatus() : 0,
                convertObjectToJson(response),
                endTime - startTime
        );
        log.info("{}", auditResponse);
        return response;
    }

    // Helper method to get request body
    private String getRequestBody(JoinPoint joinPoint) {
        String[] params = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        try {
            return Streams.zip(Arrays.stream(params), Arrays.stream(args), (param, arg) -> param + ":" + arg)
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            log.error("Error serializing request body", e);
        }
        return "";
    }

    // Helper method to convert objects to JSON
    private String convertObjectToJson(Object object) {
        if (object == null)
            return "";
        try {
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error serializing object to JSON", e);
            return "Error serializing object to JSON";
        }
    }
}
