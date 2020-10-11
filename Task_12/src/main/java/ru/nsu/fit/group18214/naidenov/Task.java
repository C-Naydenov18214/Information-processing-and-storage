package ru.nsu.fit.group18214.naidenov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Task {

    public void sortList(ArrayList list){
        list.sort((Comparator<String>) String::compareTo);
    }
}
