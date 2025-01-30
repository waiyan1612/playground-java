package com.example.playground.base.model;

public record AuditRequest(String user, String op, String URI, String body, long ts_ms) {
}
