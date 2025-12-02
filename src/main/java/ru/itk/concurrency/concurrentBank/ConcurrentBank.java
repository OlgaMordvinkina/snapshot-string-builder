package ru.itk.concurrency.concurrentBank;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentBank {
  private final Map<Long, BankAccount> accounts = new HashMap<>();
  private final AtomicLong accountCounter = new AtomicLong(1);

  public BankAccount createAccount(long initialBalance) {
    long accountNumber = accountCounter.getAndIncrement();
    BankAccount account = new BankAccount(accountNumber, initialBalance);
    synchronized (accounts) {
      accounts.put(accountNumber, account);
    }
    return account;
  }

  public void transfer(BankAccount from, BankAccount to, long amount) {
    BankAccount firstLock = from.getAccountNumber() < to.getAccountNumber() ? from : to;
    BankAccount secondLock = from.getAccountNumber() < to.getAccountNumber() ? to : from;

    synchronized (firstLock) {
      synchronized (secondLock) {
        from.withdraw(amount);
        to.deposit(amount);
      }
    }
  }

  public long getTotalBalance() {
    synchronized (accounts) {
      return accounts.values().stream().mapToLong(BankAccount::getBalance).sum();
    }
  }

  public static void main(String[] args) {
    ConcurrentBank bank = new ConcurrentBank();

    // Создание счетов
    BankAccount account1 = bank.createAccount(1000);
    BankAccount account2 = bank.createAccount(500);

    // Перевод между счетами
    Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, 200));
    Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, 100));

    transferThread1.start();
    transferThread2.start();

    try {
      transferThread1.join();
      transferThread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Вывод общего баланса
    System.out.println("Total balance: " + bank.getTotalBalance());
  }
}