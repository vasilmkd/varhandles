package io.vasilev;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class Tail {
    private volatile int tail;
    private int plainTail;

    Tail(int tl) {
        this.plainTail = tl;
        TAIL.lazySet(this, tl);
    }

    int loadPlain() {
        return plainTail;
    }

    int loadAcquire() {
        return TAIL.get(this);
    }

    void storeRelease(int tl) {
        TAIL.lazySet(this, tl);
        plainTail = tl;
    }

    private static final AtomicIntegerFieldUpdater<Tail> TAIL =
            AtomicIntegerFieldUpdater.newUpdater(Tail.class, "tail");
}
