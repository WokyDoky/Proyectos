package testing;
public class Counter extends Thread {  // active class
    private int count, inc, delay;
    public Counter(int init, int inc, int delay) {
        this.count = init; this.inc = inc; this.delay = delay;
    }
    public void run() {
        try {
            for (;;) {
                System.out.print(count + " ");
                count += inc;
                Thread.sleep(delay); // suspend delay milliseconds
            }
        } catch (InterruptedException ignored) {}
    }
    public static void main(String[] args) {
        new Counter(0, 1, 1000).start();
        new Counter(0, -1, 3000).start();
    }
}

