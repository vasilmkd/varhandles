package io.vasilev;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class ReleaseAcquire {
    int counter = 0;

    volatile String turn = "ping";
    private static final VarHandle TURN;

    static {
        try {
            TURN = MethodHandles.lookup().findVarHandle(ReleaseAcquire.class, "turn", String.class);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    void ping() {
        while (true) {
            while (!((String) TURN.getAcquire(this)).equals("ping"));

            final var current = counter++;
            System.out.println("Ping counter: " + current);
            TURN.setRelease(this, "pong");
        }
    }

    void pong() {
        while (true) {
            while (!((String) TURN.getAcquire(this)).equals("pong"));

            final var current = counter++;
            System.out.println("Pong counter: " + current);
            TURN.setRelease(this, "ping");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final var ra = new ReleaseAcquire();

        final var t1 = new Thread(ra::ping);
        t1.start();

        final var t2 = new Thread(ra::pong);
        t2.start();

        t1.join();
        t2.join();
    }
}
