package ru.nsu.fit.group18214.naidenov;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    private Master master;
    private DatabaseProvider databaseProvider;
    private List<Person> persons;


    public Worker(Master master) {
        this.master = master;
        databaseProvider = new DatabaseProvider();
        persons = new ArrayList<>();
    }

    @Override
    public void run() {
        databaseProvider.connect();
        Person tmp;
        while (true) {
            try {
                tmp = master.getPerson();
            } catch (InterruptedException e) {
                break;
            }
            persons.add(tmp);
            if (persons.size() == 50) {
                processData(persons);
                persons.clear();
            }
        }
        if (!persons.isEmpty()) {
            databaseProvider.insertPersons(persons);
        }
        databaseProvider.closeConnection();
    }

    private void processData(List<Person> persons) {
        databaseProvider.insertPersons(persons);
        for (Person p : persons) {
            String gender = p.gender;
            if (!p.daughters.isEmpty()) {
                databaseProvider.insertRelations("daughters", "daughter_id", p.getId(), new ArrayList<>(p.daughters));
                if ((gender.compareTo("male") == 0)) {
                    databaseProvider.insertRelations("fathers", "child_id", p.getId(), new ArrayList<>(p.daughters));
                } else {
                    databaseProvider.insertRelations("mothers", "child_id", p.getId(), new ArrayList<>(p.daughters));
                }
            }
            if (!p.brothers.isEmpty()) {
                databaseProvider.insertRelations("brothers", "brother_id", p.getId(), new ArrayList<>(p.brothers));
            }
            if (!p.sons.isEmpty()) {
                databaseProvider.insertRelations("sons", "son_id", p.getId(), new ArrayList<>(p.sons));
                if ((gender.compareTo("male") == 0)) {
                    databaseProvider.insertRelations("fathers", "child_id", p.getId(), new ArrayList<>(p.sons));
                } else {
                    databaseProvider.insertRelations("mothers", "child_id", p.getId(), new ArrayList<>(p.sons));
                }
            }
            if (!p.sisters.isEmpty()) {
                databaseProvider.insertRelations("sisters", "sister_id", p.getId(), new ArrayList<>(p.sisters));
            }
            if (p.husband != null) {
                databaseProvider.insertRelations("husbands", "husband_id", p.getId(), p.getHusband());
            }
            if (p.wife != null) {
                databaseProvider.insertRelations("wifes", "wife_id", p.getId(), p.getWife());
            }

        }
    }


}
