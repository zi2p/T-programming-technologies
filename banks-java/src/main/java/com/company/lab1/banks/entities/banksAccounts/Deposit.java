package com.company.lab1.banks.entities.banksAccounts;


import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

import java.time.LocalDateTime;

public class Deposit implements BankAccount {
    /* счет, с которого нельзя снимать и переводить деньги до тех пор, пока не закончится его срок (пополнять можно).
         Процент на остаток зависит от изначальной суммы, например, если открываем депозит до 50 000 р. - 3%,
         если от 50 000 р.до 100 000 р.- 3.5%, больше 100 000 р.- 4%.
         Комиссий нет.
         Проценты должны задаваться для каждого банка свои.*/
    private Bank myBank;
    private QueueOfResponsibilities queue = new QueueOfResponsibilities();
    private double amount;
    private double sum;
    private Triplet<String, Double, LocalDateTime> lastTransaction;
    private double nowPercentages;
    private MethodPercentageChange percentageChange;
    public LocalDateTime date;

    public Deposit(double sum, LocalDateTime date, MethodPercentageChange percentageChange) {
        amount = sum;
        nowPercentages = getPercentages(amount, percentageChange);
        this.date = date;
        this.sum = amount * getPercentages(amount, percentageChange);
        this.percentageChange = percentageChange;
    }

    private void setAmount(double amount) { this.amount =amount; }
    private void setSum(double sum) { this.sum =sum; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> lastTransaction) { this.lastTransaction =lastTransaction; }

    private double getSum() { return sum; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return lastTransaction; }
    private double getNowPercentages() { return nowPercentages; }

    public LocalDateTime getDate() { return date; }

    private double getPercentages(double sum, MethodPercentageChange method) { return method.getPercentage(sum); }
    public void setMyBank(Bank bank) { myBank = bank; }
    public void setPercentageMethod(MethodPercentageChange methodPercentageChange) { percentageChange = methodPercentageChange; }

    public double getPercentages() { return 0; }
    public double getCommission() { return 0; }
    public void appointСommission(double commission) { }
    public double getTheAmountOnTheAccount() { return amount; }
    public void appointPercentages(double proc) {
        if (lastTransaction ==null) {return;}
        LocalDateTime dataTime = LocalDateTime.now();
        if (isDayPassed(dataTime)) {
            sum += amount * getPercentages(amount, percentageChange);}
    }
    public void addingInterestToTheAmount() { // когда позволит ценральный банк
        if (lastTransaction ==null) {return;}
        LocalDateTime dataTime = LocalDateTime.now();
        if (isMonthPassed(dataTime)) {amount += sum;}
    }
    private boolean isDayPassed(LocalDateTime dateTime) {
        return myBank.getMyCentralBank().dayPassed(lastTransaction.getValue2(), dateTime);
    }
    private boolean isMonthPassed(LocalDateTime dateTime) {
        return myBank.getMyCentralBank().monthPassed(lastTransaction.getValue2(), dateTime);
    }

    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        if (dateTime.compareTo(date) < 0 || !(amount > sum)) { return 0; }
        amount -= sum;
        queue.bankingOperation(this);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        amount += sum;
        queue.bankingOperation(this);
        lastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        if (dateTime.compareTo(date) < 0) { return; }
        if (!(sum < amount) || person == null) { return; }
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
