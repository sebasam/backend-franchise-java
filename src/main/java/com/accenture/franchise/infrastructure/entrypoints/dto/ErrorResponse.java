package com.accenture.franchise.infrastructure.entrypoints.dto;

public record ErrorResponse(int status, String message, long timestamp) {}