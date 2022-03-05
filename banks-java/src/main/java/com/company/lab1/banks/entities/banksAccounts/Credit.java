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
    private Bank myBank;
    private double commission;
    private double amount;
    private QueueOfResponsibilities queue = new QueueOfResponsibilities();
    private Triplet<String, Double, LocalDateTime> lastTransaction;

    public Credit(double commission) {
        this.commission = commission;
        amount = 0;
    }

    private void setAmount(double amount) { this.amount = amount; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> transaction) { lastTransaction = transaction; }

    private double getAmount() { return amount; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return lastTransaction; }

    public void setMyBank(Bank bank) { myBank = bank; }

    public double getPercentages() { return 0; }
    public double getCommission() { return commission; }
    public double getTheAmountOnTheAccount() { return amount; }

    public void appointСommission(double commission) {
        if (lastTransaction ==null) { return; }
        LocalDateTime dataTime = LocalDateTime.now();
        if (amount < 0 && myBank.getMyCentralBank().dayPassed(lastTransaction.getValue2(), dataTime)) amount -= this.commission;
    }
    public void appointPercentages(double proc) { }
    public void addingInterestToTheAmount() { }
    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        amount -= sum;
        var t = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        queue.bankingOperation(this);
        lastTransaction = t;
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        amount += sum;
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        amount -= sum;
        person.setMoney(sum, dateTime);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
    }

    public void deleteLastTransaction() { // у мошенников деньги мы уже не заберем
        if (Objects.equals(lastTransaction.getValue0(), "+")) {
            cashWithdrawal(lastTransaction.getValue1(), lastTransaction.getValue2());
        } else {
            topUpYourAccount(lastTransaction.getValue1(), lastTransaction.getValue2());
        }
        lastTransaction = null;
    }
}
