package com.company.lab1.banks.services.factory;

import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.banksAccounts.Credit;
import com.company.lab1.banks.entities.banksAccounts.Debit;
import com.company.lab1.banks.entities.banksAccounts.Deposit;

import java.time.LocalDateTime;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class FactoryConsole implements Factory {
    private BankAccount account;
    private double fakeComissions = 0;
    private double fakeProcent = 0;
    private Bank bank;

    public FactoryConsole(Bank bank) {
        this.bank = bank;
    }

    public void setAccount(BankAccount account) { this.account = account; }
    public BankAccount createdBankAccount(int account) {
        var scanner = new Scanner(in);
        double sum;
        BankAccount bankAccount = null;

        switch (account) {
            case 1, 2 -> {
                out.print("Какую сумму Вы кладете на счет? ");
                sum = Double.parseDouble(scanner.nextLine());
                switch (account) {
                    case 1 -> bankAccount = new Debit(sum, fakeProcent);
                    case 2 -> {
                        LocalDateTime dataTime = LocalDateTime.now();
                        bankAccount = new Deposit(sum, dataTime, bank.getPercentageChange());
                    }
                }
            }
            case 3 -> {
                sum = Double.MAX_VALUE;
                bankAccount = new Credit(fakeComissions);
            }
        }
        return bankAccount;
    }
}
