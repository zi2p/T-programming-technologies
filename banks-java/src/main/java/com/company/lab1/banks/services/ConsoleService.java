package com.company.lab1.banks.services;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.entities.methods.percentage.PercentageChange;
import com.company.lab1.banks.services.factory.FactoryConsole;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class ConsoleService implements ConsoleServices {
    /* Для взаимодействия с банком требуется реализовать консольный интерфейс,
         который будет взаимодействовать с логикой приложения, отправлять и получать данные,
         отображать нужную информацию и предоставлять интерфейс для ввода информации пользователем. */
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;

    private void makeBank(TransferLimit transferLimit, MethodPercentageChange percentageChange) {
        var cb = new CentralBank();
        cb.addBank(percentageChange, transferLimit);
    }

    private void makeBank(Bank bank) {
        var cb = new CentralBank();
        cb.addBank(bank);
        bank.setMyCentralBank(cb);
    }

    public BankAccount enterData() {
        Scanner scanner = new Scanner(in);
        String address = null;
        int passport = 0;
        double sum = 0;
        BankAccount bankAccount = null;
        out.print("Введите Ваше полное имя: ");
        String name = scanner.nextLine();
        out.print("Хотите ли ввести свои данные о паспорте и месте жительства сейчас? (да или нет)");
        String ans = scanner.nextLine();
        if (ans == "да") {
            out.print("Введите номер и сорию паспорта без пробела: ");
            passport = Integer.parseInt(scanner.nextLine());
            if (passport > MAXPASSPORT || passport < MINPASSPORT) out.print("Некорректно введен парспорт. Зайдите в ближайший банк для исправления.");
            out.print("Введите свой адрес: ");
            address = scanner.nextLine();
        }

        out.print("Каким счётом Вы хотели бы воспользоваться? (1-Дебетовый счет, 2-Депозит, 3-Кредитный счет) ");
        int account = Integer.parseInt(scanner.nextLine());
        var bank = new Bank(0);
        makeBank(bank);
        MethodPercentageChange methodPercentageChange = new PercentageChange();
        bank.setMethodPercentageChange(methodPercentageChange);
        var transferLimit = new TransferLimit(sum / 2);
        makeBank(transferLimit, methodPercentageChange);
        var myFactory = new FactoryConsole(bank);
        bankAccount = myFactory.createdBankAccount(account);
        out.print("Ваш банковский аккаунт создан. Спасибо за визит. ");
        var person = new Client(name, 0, address, passport);
        bankAccount.setMyBank(bank);
        bank.addClient(name, bankAccount, address, passport);
        return bankAccount;
    }

    public void seeMuchAccounts(BankAccount account) {
        out.print("На вашем счёте: ");
        out.println(account.getTheAmountOnTheAccount());
    }

    public void cashWithdrawal(BankAccount account, double sum) {
        DataTime dataTime = new DataTime();
        double s = account.cashWithdrawal(sum, dataTime);
        if (s == 0) { out.print("Деньги снять не удалось. "); }
        else {
            out.print("На Вашем счёте ");
            out.print(sum - s);
        }
    }

    public void topUpYourAccount(BankAccount account, double sum) {
        DataTime dataTime = new DataTime();
        double s = account.topUpYourAccount(sum, dataTime);
        out.print("На Вашем счёте ");
        out.print(s);
    }
}