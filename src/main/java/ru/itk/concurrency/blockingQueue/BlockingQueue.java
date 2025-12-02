package ru.itk.concurrency.blockingQueue;

/**
 * Concurrency - блокирующая очередь
 */
public class BlockingQueue<T> {
  private final T[] queue;
  private final int capacity;
  private int head = 0;
  private int tail = 0;
  private int count = 0;

  @SuppressWarnings("unchecked")
  public BlockingQueue(int capacity) {
    this.capacity = capacity;
    this.queue = (T[]) new Object[capacity];
  }

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new BlockingQueue<>(5);

    Thread producer = new Thread(() -> {
      for (int i = 1; i <= 10; i++) {
        try {
          queue.enqueue(i);
          System.out.println("Produced: " + i);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });

    Thread consumer = new Thread(() -> {
      for (int i = 1; i <= 10; i++) {
        try {
          int val = queue.dequeue();
          System.out.println("Consumed: " + val);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });

    producer.start();
    consumer.start();
  }

  public synchronized void enqueue(T item) throws InterruptedException {
    while (count == capacity) {
      wait();
    }
    queue[tail] = item;
    tail = (tail + 1) % capacity;
    count++;
    notify();
  }

  public synchronized T dequeue() throws InterruptedException {
    while (count == 0) {
      wait();
    }
    T item = queue[head];
    head = (head + 1) % capacity;
    count--;
    notify();
    return item;
  }

  public synchronized int size() {
    return count;
  }
}
