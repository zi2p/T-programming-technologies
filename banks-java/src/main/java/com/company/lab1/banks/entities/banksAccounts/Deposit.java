package com.company.lab1.banks.entities.banksAccounts;


import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.entities.methods.percentage.PercentageChange;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

import java.time.LocalDateTime;

public class Deposit implements BankAccount {
    /* счет, с которого нельзя снимать и переводить деньги до тех пор, пока не закончится его срок (пополнять можно).
         Процент на остаток зависит от изначальной суммы, например, если открываем депозит до 50 000 р. - 3%,
         если от 50 000 р.до 100 000 р.- 3.5%, больше 100 000 р.- 4%.
         Комиссий нет.
         Проценты должны задаваться для каждого банка свои.*/
    public Deposit(double sum, LocalDateTime date, MethodPercentageChange percentageChange) {
        Amount = sum;
        NowPercentages = getPercentages(Amount, percentageChange);
        Date = date;
        Sum = Amount * getPercentages(Amount, percentageChange);
        PercentageChange = percentageChange;
    }

    private Bank MyBank;
    private QueueOfResponsibilities Queue = new QueueOfResponsibilities();
    private double Amount;
    private double Sum;
    private Triplet<String, Double, LocalDateTime> LastTransaction;
    private double NowPercentages;
    private MethodPercentageChange PercentageChange;

    private void setAmount(double amount) { Amount=amount; }
    private void setSum(double sum) { Sum=sum; }
    private void setLastTransaction(Triplet<String, Double, LocalDateTime> lastTransaction) { LastTransaction=lastTransaction; }

    private double getSum() { return Sum; }
    private Triplet<String, Double, LocalDateTime> getLastTransaction() { return  LastTransaction; }
    private double getNowPercentages() { return NowPercentages; }

    public LocalDateTime Date;
    public LocalDateTime getDate() { return Date; }

    private double getPercentages(double sum, MethodPercentageChange method) { return method.getPercentage(sum); }
    public void setMyBank(Bank bank) { MyBank = bank; }
    public void setPercentageMethod(MethodPercentageChange methodPercentageChange) { PercentageChange = methodPercentageChange; }

    public double getPercentages() { return 0; }
    public double getCommission() { return 0; }
    public void appointСommission(double commission) { }
    public double getTheAmountOnTheAccount() { return Amount; }
    public void appointPercentages(double proc) {
        if (LastTransaction==null) {return;}
        LocalDateTime dataTime = LocalDateTime.now();
        if (isDayPassed(dataTime)) {Sum += Amount * getPercentages(Amount, PercentageChange);}
    }
    public void addingInterestToTheAmount() { // когда позволит ценральный банк
        if (LastTransaction==null) {return;}
        LocalDateTime dataTime = LocalDateTime.now();
        if (isMonthPassed(dataTime)) Amount += Sum;
    }
    private boolean isDayPassed(LocalDateTime dateTime) {
        return MyBank.getMyCentralBank().dayPassed(LastTransaction.getValue2(), dateTime);
    }
    private boolean isMonthPassed(LocalDateTime dateTime) {
        return MyBank.getMyCentralBank().monthPassed(LastTransaction.getValue2(), dateTime);
    }

    public double cashWithdrawal(double sum, LocalDateTime dateTime) {
        if (dateTime.compareTo(Date) < 0 || !(Amount > sum)) { return 0; }
        Amount -= sum;
        Queue.bankingOperation(this);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return sum;
    }

    public double topUpYourAccount(double sum, LocalDateTime dateTime) {
        Amount += sum;
        Queue.bankingOperation(this);
        LastTransaction = new Triplet<String, Double, LocalDateTime>("-", sum, dateTime);
        return Amount;
    }

    public void transferOfMoney(double sum, Client person, LocalDateTime dateTime) {
        if (dateTime.compareTo(Date) < 0) { return; }
        if (!(sum < Amount) || person == null) { return; }
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
