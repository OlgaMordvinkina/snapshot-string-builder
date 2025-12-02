package ru.itk.concurrency.concurrentBank;

public class BankAccount {
  private final long accountNumber;
  private long balance;

  public BankAccount(long accountNumber, long initialBalance) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public synchronized void deposit(long amount) {
    balance += amount;
  }

  public synchronized void withdraw(long amount) {
    if (balance < amount) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    balance -= amount;
  }

  public synchronized long getBalance() {
    return balance;
  }
}