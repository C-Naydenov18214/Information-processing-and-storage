package ru.nsu.fit.group18214.naidenov;


import java.util.List;
import java.util.concurrent.*;

public class Source {
    private final BlockingQueue<Person> persons = new LinkedBlockingQueue<>();
    private boolean xmlEnded = false;


    public void setXmlEnded(boolean ended) {
        this.xmlEnded = ended;
    }

    public void addPersons(List<Person> p) throws InterruptedException {
        for (Person per : p) {
            persons.put(per);
        }
    }

    public Person getPerson() throws InterruptedException {
        synchronized (persons) {
            if (this.xmlEnded && persons.size() == 0) {
                throw new InterruptedException();
            }
            return persons.take();
        }
    }
}
