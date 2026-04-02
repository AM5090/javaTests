package org.test5;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    DiningPhilosophers diningPhilosophers = new DiningPhilosophers(5, 3);
    int[] result = diningPhilosophers.execute(100);

//    DiningPhilosophers diningPhilosophers = new DiningPhilosophers(3, 2);
//    int[] result = diningPhilosophers.execute(50);

//    DiningPhilosophers diningPhilosophers = new DiningPhilosophers(4, 1);
//    int[] result = diningPhilosophers.execute(200);

    System.out.println("result >>> " + Arrays.toString(result));
  }
}
