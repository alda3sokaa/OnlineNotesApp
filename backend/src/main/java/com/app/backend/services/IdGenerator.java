package com.app.backend.services; //yanlış yazmış package ismini

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private AtomicLong counter = new AtomicLong(0); //neden AtomicLong

    public long generateId() {
        return counter.incrementAndGet();
    }
}

