package com.company.lab1.banks.entities.banksAccounts;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.entities.methods.percentage.PercentageChange;
import com.company.lab1.banks.services.queue.QueueOfResponsibilities;
import org.javatuples.Triplet;

public class Deposit implements BankAccount {
    /* счет, с которого нельзя снимать и переводить деньги до тех пор, пока не закончится его срок (пополнять можно).
         Процент на остаток зависит от изначальной суммы, например, если открываем депозит до 50 000 р. - 3%,
         если от 50 000 р.до 100 000 р.- 3.5%, больше 100 000 р.- 4%.
         Комиссий нет.
         Проценты должны задаваться для каждого банка свои.*/
    public Deposit(double sum, DataTime date, MethodPercentageChange percentageChange) {
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
    private Triplet<String, Double, DataTime> LastTransaction;
    private double NowPercentages;
    private MethodPercentageChange PercentageChange;

    private void setAmount(double amount) { Amount=amount; }
    private void setSum(double sum) { Sum=sum; }
    private void setLastTransaction(Triplet<String, Double, DataTime> lastTransaction) { LastTransaction=lastTransaction; }

    private double getSum() { return Sum; }
    private Triplet<String, Double, DataTime> getLastTransaction() { return  LastTransaction; }
    private double getNowPercentages() { return NowPercentages; }

    public DataTime Date;
    public DataTime getDate() { return Date; }

    private double getPercentages(double sum, MethodPercentageChange method) { return method.getPercentage(sum); }
    public void setMyBank(Bank bank) { MyBank = bank; }
    public void setPercentageMethod(MethodPercentageChange methodPercentageChange) { PercentageChange = methodPercentageChange; }

    public double getPercentages() { return 0; }
    public double getCommission() { return 0; }
    public void appointСommission(double commission) { }
    public double getTheAmountOnTheAccount() { return Amount; }
    public void appointPercentages(double proc) {
        if (LastTransaction==null) return;
        DataTime dataTime = new DataTime();
        if (MyBank.getMyCentralBank().dayPassed(LastTransaction.getValue2(), dataTime)) Sum += Amount * getPercentages(Amount, PercentageChange);
    }
    public void addingInterestToTheAmount() { // когда позволит ценральный банк
        if (LastTransaction==null) return;
        DataTime dataTime = new DataTime();
        if (MyBank.getMyCentralBank().monthPassed(LastTransaction.getValue2(), dataTime)) Amount += Sum;
    }

    public double cashWithdrawal(double sum, DataTime dateTime) {
        if (dateTime.lessData(Date) || !(Amount > sum)) return 0;
        Amount -= sum;
        var t = new Triplet<String, Double, DataTime>("-", sum, dateTime);
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
        if (dateTime.lessData(Date)) return;
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
