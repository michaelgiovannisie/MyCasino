package com.github.zipcodewilmington.casino;

/**
 * Created by leon on 7/21/2020.
 * `ArcadeAccount` is registered for each user of the `Arcade`.
 * The `ArcadeAccount` is used to log into the system to select a `Game` to play.
 */
public class CasinoAccount {

    private String accountName;
    private String accountPassword;
    private double accountBalance;

    public CasinoAccount(String accountName, String accountPassword) {
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.accountBalance = 1000;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public boolean hasEnoughBalance(double amount) {
        return accountBalance >= amount;
    }

    public void withdraw(double amount) {
        if (hasEnoughBalance(amount)) {
            accountBalance -= amount;
        } else {
            System.out.println("Insufficient Balance.");
        }
    }

    public void deposit(double amount) {
        accountBalance += amount;
    }

}
