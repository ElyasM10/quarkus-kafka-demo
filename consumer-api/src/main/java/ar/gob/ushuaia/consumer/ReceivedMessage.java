package ar.gob.ushuaia.consumer;

public record ReceivedMessage(String key, String payload, String receivedAt) {}
