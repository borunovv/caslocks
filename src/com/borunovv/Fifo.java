package com.borunovv;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author borunovv
 */
public class Fifo {

    private ConcurrentLinkedQueue<Semaphore> queue = new ConcurrentLinkedQueue<Semaphore>();

    public void WAIT() throws InterruptedException {
        Semaphore local = new Semaphore(0);
        queue.add(local);
        local.WAIT();
    }

    public void SIGNAL() {
        Semaphore local = queue.poll();
        if (local != null) {
            local.SIGNAL();
        }
    }

    public int size() {
        return queue.size();
    }
}
