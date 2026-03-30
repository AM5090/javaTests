package org.example;

import java.util.Arrays;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelMaxFinder {

  public static int[][] findSeparateStep(int arrayLength, int threadCount) {
    int[][] separateParts = new int[threadCount][2];

    if (threadCount == 1) {
      separateParts[0] = new int[]{0, arrayLength-1};
      return separateParts;
    }

    int separateStep = (int) Math.round((double) arrayLength /threadCount);

    for(int i = 0; i < threadCount; i++) {
      if (i == 0) {
        separateParts[i] = new int[]{0, separateStep};
      } else if (i == threadCount-1) {
        separateParts[i] = new int[]{separateStep*i, arrayLength};
      } else {
        separateParts[i] = new int[]{separateStep*i, separateStep*i+separateStep};
      }
    }

    return separateParts;
  }

  public static long execute(int t, long[] array) throws InterruptedException {

    if (array.length == 0) return 0;
    if (array.length == 1) return array[0];

    Thread[] threads = new Thread[t];
    AtomicLong maxNum = new AtomicLong(Long.MIN_VALUE);

    int[][] separateParts = findSeparateStep(array.length, t);

    for(int i = 0; i < separateParts.length; i++) {
      int finalI = i;

      threads[i] = new Thread(() -> {
        long[] slice;
        if (separateParts.length == 1) {
          slice = array;
        } else {
          slice = Arrays.copyOfRange(array, separateParts[finalI][0],  separateParts[finalI][1]);
        }

        OptionalLong maxInSlice = Arrays.stream(slice).max();

        maxNum.updateAndGet(current -> {
          if (maxInSlice.isPresent()) {
            if (current == Long.MIN_VALUE || maxInSlice.getAsLong() > current) {
              return maxInSlice.getAsLong();
            }
            return current;
          }
          return current;
        });

      });
      threads[i].start();
  }

    for (Thread thr : threads) {
      thr.join();
    }

    return maxNum.longValue();
  }
}
