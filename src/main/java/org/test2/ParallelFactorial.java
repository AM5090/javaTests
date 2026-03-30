package org.test2;

import java.math.BigInteger;

public class ParallelFactorial {
  public static BigInteger execute(int t, int n) {

    Thread[] threads = new Thread[t];
    BigInteger[] allResults = new BigInteger[t];
    BigInteger result = BigInteger.ONE;

    int separateStep = n/t;

    for(int i = 0; i < t; i++) {

      final int index = i;
      final int start = i * separateStep + 1;
      final int end = (i == t-1) ? n : (i + 1) * separateStep;

      threads[i] = new Thread(() -> {
        BigInteger sliceMultiply = BigInteger.ONE;

        for (int num = start; num <= end; num++) {
          sliceMultiply = sliceMultiply.multiply(BigInteger.valueOf(num));
        }

        allResults[index] = sliceMultiply;
      });

      threads[i].start();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    for (BigInteger item : allResults) {
      result = result.multiply(item);
    }

    return result;
  }
}
