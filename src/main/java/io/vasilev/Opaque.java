package io.vasilev;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class Opaque {
    volatile boolean done = false;
    int counter = 0;
    private static final VarHandle DONE;

    static {
        try {
            DONE = MethodHandles.lookup().findVarHandle(Opaque.class, "done", boolean.class);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    void loop() {
        while (!(boolean) DONE.getOpaque(this)) {
            counter++;
            System.out.println();
        }
    }

    void setDone() {
        DONE.setOpaque(this, true);
    }

    public static void main(String[] args) throws InterruptedException {
        final var opaque = new Opaque();

        final var t1 = new Thread(opaque::loop);
        t1.start();

        final var t2 = new Thread(opaque::setDone);
        t2.start();

        System.out.println("Main joining t1");
        t1.join();
        System.out.println("Main joining t2");
        t2.join();
        System.out.println("Done");
    }
}
