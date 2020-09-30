package ru.nsu.fit.g18214.naidenov;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        int numberOfDep = 6;
        Company company = new Company(numberOfDep);
        CyclicBarrier barrier = new CyclicBarrier(numberOfDep+1, company::showCollaborativeResult);

        Founder founder = new Founder(company,barrier);
        founder.setDepartment();
        founder.start();
        barrier.await();


    }
}
