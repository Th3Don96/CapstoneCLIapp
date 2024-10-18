package com.pluralsight;

public class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) ;{
            this.balance += amount;
        }
    }
    public void withdraw(double amount){
        if (amount > 0 && amount <= this.balance){
            this.balance -= amount;
        }
    }
    public double getBalance(){
        return this.balance;
    }

}
