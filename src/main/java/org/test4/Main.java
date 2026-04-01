package org.test4;

public class Main {
  public static void main(String[] args) {
//    BankAccount account = new BankAccount(1000);
//    int result = account.execute(2, 2, 50, 100);

//    BankAccount account = new BankAccount(500);
//    int result = account.execute(3, 2, 150, 200);

    BankAccount account = new BankAccount(0);
    int result = account.execute(1, 1, 500, 1000);

    System.out.println("result >>> " + result);
  }
}
