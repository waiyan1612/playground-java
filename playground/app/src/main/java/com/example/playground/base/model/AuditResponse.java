package com.example.playground.base.model;

public record AuditResponse(String user, String op, String URI, int status, String body, long duration_ms) {
}
