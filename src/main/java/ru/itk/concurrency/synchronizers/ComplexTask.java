package ru.itk.concurrency.synchronizers;

public record ComplexTask(int taskId) {
  public int execute() {
    return taskId * 2;
  }
}
