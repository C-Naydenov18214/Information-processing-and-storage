public class Worker implements  Runnable {
    private Task task;
    private String[] strings;

    public Worker(Task task,String[] strings)
    {
        this.task = task;
        this.strings = strings;
    }

    @Override
    public void run() {
        task.execute(strings);
    }
}
