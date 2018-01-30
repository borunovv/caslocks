package com.borunovv;

/**
 * @author borunovv
 */
public class ReadWriteLock {

    private Semaphore roomEmpty = new Semaphore(1);
    private LightSwitch lightSwitch = new LightSwitch(roomEmpty);

    public void LOCK_WRITE() throws InterruptedException {
        roomEmpty.WAIT();
    }

    public void UNLOCK_WRITE() {
        roomEmpty.SIGNAL();
    }

    public void LOCK_READ() throws InterruptedException {
        lightSwitch.ENTER();
    }

    public void UNLOCK_READ() throws InterruptedException {
        lightSwitch.LEAVE();
    }
}
