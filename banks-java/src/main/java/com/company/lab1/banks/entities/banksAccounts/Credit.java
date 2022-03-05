package com.company.lab1.banks.entities.banksAccounts;


import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

import java.time.LocalDateTime;
import java.util.Objects;

public class Credit implements BankAccount{
    /* имеет кредитный лимит, в рамках которого можно уходить в минус (в плюс тоже можно).
         Процента на остаток нет. Есть фиксированная комиссия за использование, если клиент в минусе.*/
    public Credit(double commission) {
        Commission = commission;
        Amount = 0;
    }

    private Bank MyBank;
    private double Commission;
    private double Amount;
    private QueueOfResponsibilities Queue = new QueueOfResponsibilities();
    private Triplet<String, Double, LocalDateTime> LastTransaction;

    private void setAmount(double amount) { Amount = amount; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> transaction) { LastTransaction = transaction; }

    private double getAmount() { return Amount; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return LastTransaction; }

    public void setMyBank(Bank bank) { MyBank = bank; }

    public double getPercentages() { return 0; }
    public double getCommission() { return Commission; }
    public double getTheAmountOnTheAccount() { return Amount; }

    public void appointСommission(double commission) {
        if (LastTransaction==null) { return; }
        LocalDateTime dataTime = LocalDateTime.now();
        if (Amount < 0 && MyBank.getMyCentralBank().dayPassed(LastTransaction.getValue2(), dataTime)) Amount -= Commission;
    }
    public void appointPercentages(double proc) { }
    public void addingInterestToTheAmount() { }
    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        Amount -= sum;
        var t = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        Queue.bankingOperation(this);
        LastTransaction = t;
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        Amount += sum;
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return Amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        Amount -= sum;
        person.setMoney(sum, dateTime);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
    }

    public void deleteLastTransaction() { // у мошенников деньги мы уже не заберем
        if (Objects.equals(LastTransaction.getValue0(), "+")) {
            cashWithdrawal(LastTransaction.getValue1(), LastTransaction.getValue2());
        } else {
            topUpYourAccount(LastTransaction.getValue1(), LastTransaction.getValue2());
        }
        LastTransaction = null;
    }
}
