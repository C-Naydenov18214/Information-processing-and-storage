import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Task task = new Task();

        Thread thread1 = new Thread(new Worker(task, new String[]{"thread1 ", "is ", "working ", "now "}));
        Thread thread2 = new Thread(new Worker(task, new String[]{"thread2 ", "is ", "working ", "now "}));
        Thread thread3 = new Thread(new Worker(task, new String[]{"thread3 ", "is ", "working ", "now "}));
        Thread thread4 = new Thread(new Worker(task, new String[]{"thread4 ", "is ", "working ", "now "}));

        ArrayList<Thread> workers = new ArrayList<>();

        workers.add(thread1);
        workers.add(thread2);
        workers.add(thread3);
        workers.add(thread4);

        for (int i = 0; i < 4; i++) {
            workers.get(i).start();
        }
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }
}
