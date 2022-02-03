package io.vasilev;

class ExpensiveResource {}

public class DoubleCheckedLocking {

    private static ExpensiveResource instance = null;

    public ExpensiveResource getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new ExpensiveResource();
                }
            }
        }
        return instance;
    }
}
