package com.company.lab1.banks.entities.banksAccounts;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.client.Client;

public interface BankAccount {
    public void appointСommission(double commission); // комиссия
    public void appointPercentages(double proc); // проценты
    public void addingInterestToTheAmount();
    public double getTheAmountOnTheAccount(); // сумма
    public double cashWithdrawal(double sum, DataTime dateTime);
    public double topUpYourAccount(double sum, DataTime dateTime);
    public void transferOfMoney(double sum, Client person, DataTime dateTime);
    public void deleteLastTransaction();
    public void setMyBank(Bank bank);
    public double getPercentages();
    public double getCommission();
}
