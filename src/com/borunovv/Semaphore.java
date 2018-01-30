package com.borunovv;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Простейший семафор на CAS (Compare and Swap).
 *
 * @author borunovv
 */
public class Semaphore {

    private AtomicInteger counter;

    public Semaphore(int capacity) {
        counter = new AtomicInteger(capacity);
    }

    public void ENTER() throws InterruptedException {
        while (counter.decrementAndGet() < 0) {
            int value = counter.incrementAndGet();
            if (value > 0 && counter.compareAndSet(value, value - 1)) {
                break;
            }
            Thread.yield();
        }
    }

    public void LEAVE() {
        counter.incrementAndGet();
    }

    // Alternate names

    public void WAIT() throws InterruptedException {
        ENTER();
    }

    public void SIGNAL() {
        LEAVE();
    }

    public void SIGNAL(int count) {
        for (int i = 0; i < count; ++i) {
            SIGNAL();
        }
    }
}
