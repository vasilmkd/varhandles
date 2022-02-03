package io.vasilev;

public class Plain {
    boolean done = false;
    int counter = 0;

    void loop() {
        while (!done) {
            counter++;
        }
    }

    void setDone() {
        done = true;
    }

    public static void main(String[] args) throws InterruptedException {
        final var plain = new Plain();

        final var t1 = new Thread(plain::loop);
        t1.start();

        Thread.sleep(5000);

        final var t2 = new Thread(plain::setDone);
        t2.start();

        System.out.println("Main joining t1");
        t1.join();
        System.out.println("Main joining t2");
        t2.join();
        System.out.println("Done");
    }
}
