package com.company.lab1.banks.entities.banksAccounts;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

public class Debit implements BankAccount {
    /* обычный счет с фиксированным процентом на остаток.
        Деньги можно снимать в любой момент, в минус уходить нельзя.
        Комиссий нет */
    public Debit(double sum, double proc) {
        var Time = new DataTime();
        Amount = sum;
        Percentages = proc;
        Sum = Amount * Percentages;
        LastTransaction = new Triplet<String, Double, DataTime>(" ", 0., Time);
    }
    public Debit(double sum) {
        var Time = new DataTime();
        Amount = sum;
        Sum = Amount * Percentages;
        LastTransaction = new Triplet<String, Double, DataTime>(" ", 0., Time);
    }

    private Bank MyBank;
    private QueueOfResponsibilities Queue = new QueueOfResponsibilities();
    private double Amount;
    private double Sum;
    private double Percentages;
    private Triplet<String, Double, DataTime> LastTransaction;

    private void setSum(double sum) { Sum = sum; }
    private void setLastTransaction(Triplet<String, Double, DataTime> lastTransaction) { LastTransaction = lastTransaction; }
    private void setAmount(double amount) { Amount = amount; }

    private double getSum() { return Sum; }
    private Triplet<String, Double, DataTime> getLastTransaction() { return LastTransaction; }
    private double getAmount() { return Amount; }

    public void setPercentage(double proc) { Percentages = proc; }
    public void setMyBank(Bank bank) { MyBank = bank; }

    public double getTheAmountOnTheAccount() { return Amount; }
    public double getPercentages() { return Percentages; }
    public double getCommission() { return 0; }

    public void appointСommission(double commission) { }
    public void appointPercentages(double proc) {
        DataTime dataTime = new DataTime();
        if (MyBank.getMyCentralBank().dayPassed(LastTransaction.getValue2(), dataTime)) Sum += Amount * Percentages;
    }

    public void addingInterestToTheAmount() {
        DataTime dataTime = new DataTime();
        if (MyBank.getMyCentralBank().monthPassed(LastTransaction.getValue2(), dataTime)) Amount += Sum;
    }

    public double cashWithdrawal(double sum, DataTime dateTime) {
        if (!(Amount > sum)) return 0; // столько мы можем снять (ложный код возврата)
        Amount -= sum;
        var t = new Triplet<String, Double, DataTime>("+", sum, dateTime);
        Queue.bankingOperation(this);
        LastTransaction = t;
        return sum;
    }

    public double topUpYourAccount(double sum, DataTime dateTime) {
        Amount += sum;
        var t = new Triplet<String, Double, DataTime>("-", sum, dateTime);
        Queue.bankingOperation(this);
        LastTransaction = t;
        return Amount;
    }

    public void transferOfMoney(double sum, Client person, DataTime dateTime) {
        if (!(sum < Amount) || person == null) return;
        Amount -= sum;
        person.setMoney(sum, dateTime);
        var t = new Triplet<String, Double, DataTime>("-", sum, dateTime);
        Queue.bankingOperation(this);
        LastTransaction = t;
    }

    public void deleteLastTransaction() { // у мошенников деньги мы уже не заберем
        if (LastTransaction.getValue0() == "+") {
            cashWithdrawal(LastTransaction.getValue1(), LastTransaction.getValue2());
        }
        else {
            topUpYourAccount(LastTransaction.getValue1(), LastTransaction.getValue2());
        }
        LastTransaction = null;
    }
}
