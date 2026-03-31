package org.test3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelPrimeFinder {
  public static List<Long> execute(int t, long l, long r) throws InterruptedException {
    Thread[] threads = new Thread[t];

    long separateStep = (r - l + 1) / t;

    List<Long> results = Collections.synchronizedList(new ArrayList<>());

    for(int i = 0; i < t; i++) {

      final long start = l + i * separateStep;
      final long end = (i == t - 1) ? r : l + (i + 1) * separateStep - 1;


      threads[i] = new Thread(() -> {
        for(long num = start; num <= end; num++) {
          boolean isPrime = true;
          if (num < 2) continue;

          for (int j = 2; j <= Math.sqrt(num); j++) {
            if (num % j == 0) {
              isPrime = false;
              break;
            }
          }
          if (isPrime) {
            results.add(num);
          }
        }
      });

      threads[i].start();
    }

    for (Thread thread: threads) {
      thread.join();
    }

    Collections.sort(results);
    return results;
  }
}
