package org.test4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition withdrawCondition = lock.newCondition();
  private int balance;

  public BankAccount(int balance) {
    this.balance = balance;
  }

  public int execute(int n, int m, int withdrawalAmount, int depositAmount) {
    Thread[] withdrawThreads = new Thread[n];
    Thread[] depositThreads = new Thread[m];


    for (int i = 0; i < n; i++) {
      withdrawThreads[i] = new Thread(() -> {
        try {
          this.withdraw(withdrawalAmount);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " : " + balance);
      }, "Withdraw-" + i);
      withdrawThreads[i].start();
    }

    for (int i = 0; i < m; i++) {
      depositThreads[i] = new Thread(() -> {
        this.deposit(depositAmount);
        System.out.println(Thread.currentThread().getName() + " : " + balance);
      }, "Deposit-" + i);
      depositThreads[i].start();
    }

    for(Thread thread : withdrawThreads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    for(Thread thread : depositThreads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    return balance;
  }

  public void withdraw(int amount) throws InterruptedException {
    lock.lock();

    while (balance < amount) {
        withdrawCondition.await();
    }

    try {
      balance -= amount;
    } finally {
      lock.unlock();
    }
  }

  public void deposit(int amount) {
    lock.lock();

    try {
      balance += amount;
      withdrawCondition.signalAll();
    } finally {
      lock.unlock();
    }
  }
}
