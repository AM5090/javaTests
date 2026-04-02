package org.test5;

import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {
  private final ReentrantLock lock = new ReentrantLock();
  private final int philosophers;
  private final int meals;
  int[] results;

  public DiningPhilosophers(int philosophers, int meals) {
    this.philosophers = philosophers;
    this.meals = meals;
    results = new int[philosophers];
  }

  public void changeResults(long t, int philosopherIndex) {
    lock.lock();
    int currentMealCount = 0;

    try {
      while (currentMealCount < meals) {
        try {
          results[philosopherIndex] += 1;
          Thread.sleep(t);
          currentMealCount++;
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public int[] execute(long t) throws InterruptedException {

    Thread[] philosophersThread = new Thread[philosophers];


    for (int i = 0; i < philosophersThread.length; i++) {

      int finalI = i;

      philosophersThread[i] = new Thread(() -> {
        changeResults(t, finalI);
      });
      philosophersThread[i].start();
    }

    for(Thread thread : philosophersThread) {
      thread.join();
    }

    return results;
  }
}
