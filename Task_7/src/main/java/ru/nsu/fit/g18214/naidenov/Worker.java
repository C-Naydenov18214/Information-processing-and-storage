package ru.nsu.fit.g18214.naidenov;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    private ArrayList<Double> tmpRes;
    private ArrayList<Integer> number;

    public Worker() {
        this.tmpRes = new ArrayList<>();
        this.number = new ArrayList<>();
    }

    public void addN(int i) {
        number.add(i);
    }

    public double getResN(int n){
        return tmpRes.get(n);
    }

    public int getResSize(){
        return tmpRes.size();
    }

    @Override
    public void run() {
        for (double n : number) {
            double tmp = Math.pow(-1, n) / (2 * n + 1);
            tmpRes.add(tmp);
        }
    }
}
