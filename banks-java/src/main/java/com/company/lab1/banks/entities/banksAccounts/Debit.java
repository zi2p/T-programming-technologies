package com.company.lab1.banks.entities.banksAccounts;


import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

import java.time.LocalDateTime;

public class Debit implements BankAccount {
    /* обычный счет с фиксированным процентом на остаток.
        Деньги можно снимать в любой момент, в минус уходить нельзя.
        Комиссий нет */
    public Debit(double sum, double proc) {
        var time = LocalDateTime.now();
        Amount = sum;
        Percentages = proc;
        Sum = Amount * Percentages;
        LastTransaction = new Triplet<String, Double, LocalDateTime>(" ", 0., time);
    }
    public Debit(double sum) {
        var time = LocalDateTime.now();
        Amount = sum;
        Sum = Amount * Percentages;
        LastTransaction = new Triplet<String, Double, LocalDateTime>(" ", 0., time);
    }

    private Bank MyBank;
    private QueueOfResponsibilities Queue = new QueueOfResponsibilities();
    private double Amount;
    private double Sum;
    private double Percentages;
    private Triplet<String, Double, LocalDateTime> LastTransaction;

    private void setSum(double sum) { Sum = sum; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> lastTransaction) { LastTransaction = lastTransaction; }
    private void setAmount(double amount) { Amount = amount; }

    private double getSum() { return Sum; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return LastTransaction; }
    private double getAmount() { return Amount; }

    public void setPercentage(double proc) { Percentages = proc; }
    public void setMyBank(Bank bank) { MyBank = bank; }

    public double getTheAmountOnTheAccount() { return Amount; }
    public double getPercentages() { return Percentages; }
    public double getCommission() { return 0; }

    public void appointСommission(double commission) { }
    public void appointPercentages(double proc) {
        var dataTime = LocalDateTime.now();
        if (isDayPassed(dataTime)) {Sum += Amount * Percentages;}
    }
    private boolean isDayPassed(LocalDateTime dateTime) {
        return MyBank.getMyCentralBank().dayPassed(LastTransaction.getValue2(), dateTime);
    }
    private boolean isMonthPassed(LocalDateTime dateTime) {
        return MyBank.getMyCentralBank().monthPassed(LastTransaction.getValue2(), dateTime);
    }

    public void addingInterestToTheAmount() {
        var dataTime = LocalDateTime.now();
        if (isMonthPassed(dataTime)) { Amount += Sum; }
    }

    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        if (!(Amount > sum)) { return 0; } // столько мы можем снять (ложный код возврата)
        Amount -= sum;
        Queue.bankingOperation(this);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("+", sum, dateTime);
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        Amount += sum;
        Queue.bankingOperation(this);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return Amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        if (!(sum < Amount) || person == null) return;
        Amount -= sum;
        person.setMoney(sum, dateTime);
        Queue.bankingOperation(this);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
    }

    public void deleteLastTransaction() { // у мошенников деньги мы уже не заберем
        if (LastTransaction.getValue0() == "+") {
            cashWithdrawal(LastTransaction.getValue1(), LastTransaction.getValue2());
        } else {
            topUpYourAccount(LastTransaction.getValue1(), LastTransaction.getValue2());
        }
        LastTransaction = null;
    }
}
