package com.borunovv;

/**
 * @author borunovv
 */
public class LightSwitch {

    private Semaphore roomEmpty;
    private Semaphore mutex = new Semaphore(1);
    private int count = 0;

    public LightSwitch(Semaphore lock) {
        this.roomEmpty = lock;
    }

    public void ENTER() throws InterruptedException {
        mutex.WAIT();
        {
            count++;
            if (count == 1) {
                // Первый вошедший включает свет, либо ждет, пока выключат. Остальные ждут на мютексе.
                roomEmpty.WAIT();
            }
        }
        mutex.SIGNAL();
    }

    public void LEAVE() throws InterruptedException {
        mutex.WAIT();
        {
            count--;
            if (count == 0) {
                // Последний - "выключает свет".
                roomEmpty.SIGNAL();
            }
        }
        mutex.SIGNAL();
    }
}
