package ru.nsu.fit.g18214.naidenov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Founder {
  private final List<Runnable> workers;
  private Company company;
  private CyclicBarrier barrier;

  public Founder(final Company company, CyclicBarrier bar) {
    this.barrier = bar;
    this.company = company;
    this.workers = new ArrayList<>(company.getDepartmentsCount());
  }

  public void setDepartment() {

    for (int i = 0; i < company.getDepartmentsCount(); i++) {
      Department dep = company.getFreeDepartment(i);
      workers.add(
              () -> {
                dep.performCalculations();
                try {
                  barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                  e.printStackTrace();
                }
              });
    }
  }

  public void start() {
    for (final Runnable worker : workers) {
      new Thread(worker).start();
    }
  }
}
