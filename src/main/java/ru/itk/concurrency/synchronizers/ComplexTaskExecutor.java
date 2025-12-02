package ru.itk.concurrency.synchronizers;

import java.util.concurrent.*;

public record ComplexTaskExecutor(int defaultNumberOfTasks) {

  public static void main(String[] args) {
    // Немножко непонятно зачем тут передаётся количество задач, если оно же передается параметром метода
    ComplexTaskExecutor taskExecutor = new ComplexTaskExecutor(5); // Количество задач для выполнения

    Runnable testRunnable = () -> {
      System.out.println(Thread.currentThread().getName() + " started the test.");

      // Выполнение задач
      taskExecutor.executeTasks(5);

      System.out.println(Thread.currentThread().getName() + " completed the test.");
    };

    Thread thread1 = new Thread(testRunnable, "TestThread-1");
    Thread thread2 = new Thread(testRunnable, "TestThread-2");

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void executeTasks(int numberOfTasks) {
    int tasks = numberOfTasks > 0 ? numberOfTasks : defaultNumberOfTasks;

    ExecutorService executor = Executors.newFixedThreadPool(tasks);
    CyclicBarrier barrier = new CyclicBarrier(tasks, () -> {
      System.out.println("All tasks have reached the barrier.");
    });

    @SuppressWarnings("unchecked")
    Future<Integer>[] results = new Future[tasks];

    for (int i = 0; i < tasks; i++) {
      final int taskId = i;
      results[i] = executor.submit(() -> {
        ComplexTask task = new ComplexTask(taskId);
        int result = task.execute();
        barrier.await();
        return result;
      });
    }

    for (Future<Integer> future : results) {
      try {
        System.out.println("Task result: " + future.get());
      } catch (InterruptedException | ExecutionException e) {
        Thread.currentThread().interrupt();
      }
    }

    executor.shutdown();
  }

}