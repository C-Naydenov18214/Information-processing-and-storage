public class Task {
    synchronized public void execute(String[] args) {
        int len = args.length;
        for (int i = 0; i < len; i++) {
            System.out.print(args[i]);

        }
        System.out.println();

    }
}
