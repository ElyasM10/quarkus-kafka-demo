package ar.gob.ushuaia.producer;

public record MessageResponse(String status, String key, String payload) {}
