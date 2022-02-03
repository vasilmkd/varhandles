package io.vasilev;

public class Volatile {
    int counter = 0;
    volatile String turn = "ping";

    void ping() {
        while (true) {
            while (!turn.equals("ping"));

            final var current = counter++;
            System.out.println("Ping counter: " + current);
            turn = "pong";
        }
    }

    void pong() {
        while (true) {
            while (!turn.equals("pong"));

            final var current = counter++;
            System.out.println("Pong counter: " + current);
            turn = "ping";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final var v = new Volatile();

        final var t1 = new Thread(v::ping);
        t1.start();

        final var t2 = new Thread(v::pong);
        t2.start();

        t1.join();
        t2.join();
    }
}
