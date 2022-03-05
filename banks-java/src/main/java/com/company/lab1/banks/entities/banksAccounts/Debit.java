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
    private Bank myBank;
    private QueueOfResponsibilities queue = new QueueOfResponsibilities();
    private double amount;
    private double sum;
    private double percentages;
    private Triplet<String, Double, LocalDateTime> lastTransaction;

    public Debit(double sum, double proc) {
        var time = LocalDateTime.now();
        amount = sum;
        percentages = proc;
        this.sum = amount * percentages;
        lastTransaction = new Triplet<String, Double, LocalDateTime>(" ", 0., time);
    }
    public Debit(double sum) {
        this(sum, 0);
    }

    private void setSum(double sum) { this.sum = sum; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> lastTransaction) { this.lastTransaction = lastTransaction; }
    private void setAmount(double amount) { this.amount = amount; }

    private double getSum() { return sum; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return lastTransaction; }
    private double getAmount() { return amount; }

    public void setPercentage(double proc) { percentages = proc; }
    public void setMyBank(Bank bank) { myBank = bank; }

    public double getTheAmountOnTheAccount() { return amount; }
    public double getPercentages() { return percentages; }
    public double getCommission() { return 0; }

    public void appointСommission(double commission) { }
    public void appointPercentages(double proc) {
        var dataTime = LocalDateTime.now();
        if (isDayPassed(dataTime)) {sum += amount * percentages;}
    }
    private boolean isDayPassed(LocalDateTime dateTime) {
        return myBank.getMyCentralBank().dayPassed(lastTransaction.getValue2(), dateTime);
    }
    private boolean isMonthPassed(LocalDateTime dateTime) {
        return myBank.getMyCentralBank().monthPassed(lastTransaction.getValue2(), dateTime);
    }

    public void addingInterestToTheAmount() {
        var dataTime = LocalDateTime.now();
        if (isMonthPassed(dataTime)) { amount += sum; }
    }

    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        if (!(amount > sum)) { return 0; } // столько мы можем снять (ложный код возврата)
        amount -= sum;
        queue.bankingOperation(this);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("+", sum, dateTime);
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        amount += sum;
        queue.bankingOperation(this);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        if (!(sum < amount) || person == null) {return;}
        amount -= sum;
        person.setMoney(sum, dateTime);
        queue.bankingOperation(this);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
    }

    public void deleteLastTransaction() { // у мошенников деньги мы уже не заберем
        if (lastTransaction.getValue0() == "+") {
            cashWithdrawal(lastTransaction.getValue1(), lastTransaction.getValue2());
        } else {
            topUpYourAccount(lastTransaction.getValue1(), lastTransaction.getValue2());
        }
        lastTransaction = null;
    }
}
