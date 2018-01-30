package com.borunovv;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Барьер на семафорах.
 *
 * @author borunovv
 */
public class Barrier {

    // Два турникета нужны, чтобы была возможность использовать барьер в цикле.
    private Semaphore turnstile1 = new Semaphore(0); // закрыт
    private Semaphore turnstile2 = new Semaphore(0); // закрыт
    private int capacity;
    private AtomicInteger waitingThreads = new AtomicInteger(0);

    public Barrier(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        this.capacity = capacity;
    }

    public void WAIT() throws InterruptedException {
        if (waitingThreads.incrementAndGet() == capacity) {
            // Последний поток открывает ПЕРВЫЙ турникет сразу для _всех_ #capacity потоков
            turnstile1.SIGNAL(capacity); //
        }
        turnstile1.WAIT();

        // Тут можно выполнять что-то параллельное, выделив 2 фазы (ожидание на турникетах #1 и #2).

        if (waitingThreads.decrementAndGet() == 0) {
            // Последний поток открывает ВТОРОЙ турникет сразу для _всех_ #capacity потоков
            turnstile2.SIGNAL(capacity);
        }
        turnstile2.WAIT();
    }
}
