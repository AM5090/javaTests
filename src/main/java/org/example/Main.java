package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long[] arrayLong = {42};
        int threadCount = 2;

        long maxInt = ParallelMaxFinder.execute(threadCount, arrayLong);
        System.out.println("Max: " + maxInt);
    }
}