package com.app.backend.services;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {

    private final AtomicLong counter = new AtomicLong(0);

    public long generateId() {
        return counter.incrementAndGet();
    }
}