package org.test3;

import java.util.List;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List result = ParallelPrimeFinder.execute(2, 1, 10);
//    List result = ParallelPrimeFinder.execute(3, 10, 20);
//    List result = ParallelPrimeFinder.execute(4, 14, 16);


    System.out.println("result >>> " + result);

  }
}
