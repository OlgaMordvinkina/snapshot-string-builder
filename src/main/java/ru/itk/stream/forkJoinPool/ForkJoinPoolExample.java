package ru.itk.stream.forkJoinPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<Long> {
  private final int start;
  private final int end;

  public FactorialTask(int n) {
    this.start = 1;
    this.end = n;
  }

  private FactorialTask(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    if (end - start <= 2) {
      long r = 1;
      for (int i = start; i <= end; i++) r *= i;
      return r;
    }

    int mid = (start + end) / 2;

    FactorialTask task1 = new FactorialTask(start, mid);
    FactorialTask task2 = new FactorialTask(mid + 1, end);

    task1.fork();
    long result1 = task2.compute();
    long result2 = task1.join();

    return result2 * result1;
  }
}

public class ForkJoinPoolExample {
  public static void main(String[] args) {
    int n = 10; // Вычисление факториала для числа 10

    ForkJoinPool forkJoinPool = new ForkJoinPool();
    FactorialTask factorialTask = new FactorialTask(n);

    long result = forkJoinPool.invoke(factorialTask);

    System.out.println("Факториал " + n + "! = " + result);
  }
}
