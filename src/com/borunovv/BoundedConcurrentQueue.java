package com.borunovv;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Потокобезопасная очередь фиксированного размера.
 * Реализация стратегии "Producer-Consumer".
 * При полной очереди - потоки Producer'ов переходят в режим ожидания.
 * При пустой очереди - потоки Consumer'ов также переходят в режим ожидания.
 *
 * @author borunovv
 */
public class BoundedConcurrentQueue<T> {

    private Semaphore freeSpace;
    private Semaphore nonEmpty;
    private ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<T>();

    public BoundedConcurrentQueue(int capacity) {
        this.freeSpace = new Semaphore(capacity);
        this.nonEmpty = new Semaphore(0);
    }

    public void put(T item) throws InterruptedException {
        freeSpace.WAIT();
        queue.add(item);
        nonEmpty.SIGNAL();
    }

    public T get() throws InterruptedException {
        nonEmpty.WAIT();
        T item = queue.poll();
        freeSpace.SIGNAL();
        return item;
    }

    public int size() {
        return queue.size();
    }
}
