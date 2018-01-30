package com.borunovv;

/**
 * Блокировка для идиомы "Writer-Reader" с приоритетом у Writer'a
 * (при появлении writer'a новые reader'ы будут ждать, чтобы избежать "writer starvation").
 *
 * @author borunovv
 */
public class ReadWriteNoStarvateLock {

    private Semaphore roomEmpty = new Semaphore(1);
    private LightSwitch readerLightSwitch = new LightSwitch(roomEmpty);
    private Semaphore readerTurnstile = new Semaphore(1); // Турникет для читателей.

    public void LOCK_WRITE() throws InterruptedException {
        readerTurnstile.WAIT(); // Блокируем турникет ридеров
        roomEmpty.WAIT();       // Ждем пустую комнату
    }

    public void UNLOCK_WRITE() {
        readerTurnstile.SIGNAL(); // Открываем турникет ридеров.
        roomEmpty.SIGNAL();       // Освобождаем комнату.
    }

    public void LOCK_READ() throws InterruptedException {
        readerTurnstile.WAIT();   // Ждем на турникете.
        readerTurnstile.SIGNAL(); // Оставляем после себя турникет открытым.
        readerLightSwitch.ENTER();// Ломимся в комнату.
    }

    public void UNLOCK_READ() throws InterruptedException {
        readerLightSwitch.LEAVE();
    }
}
