package com.app.backend.utils;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {

    private final AtomicLong id = new AtomicLong(0);

    public long generateId() {
        return id.incrementAndGet();
    }
}