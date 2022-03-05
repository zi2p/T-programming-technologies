package com.company.lab1.banks.entities.client;


import com.company.lab1.banks.entities.banksAccounts.BankAccount;

import java.time.LocalDateTime;
import java.util.Objects;

public class Client {
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;
    private String fakeAdres = " ";
    private int fakePassport = 0;

    public String address;
    public int passport;
    public int Phone;
    public int id;
    public BankAccount bankAccount;
    private String name;

    public Client(String name, int id, String address, int passport) {
        this.name = name;
        this.address = address;
        this.passport = passport;
        this.id = id;
        bankAccount = null;
    }

    public Client(String name, int id) {
        this(name, id, null, 0);
    }

    public Client(String name, int id, int passport) {
        this(name, id, null, passport);
    }

    public Client(String name, int id, String address) {
        this(name, id, address, 0);
    }

    private String getName() { return name; }

    public void setPassport(int passport) { this.passport =passport; }
    public void setPhone(int phone) { Phone=phone; }
    public void setAddress(String address) { this.address =address; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount =bankAccount; }

    public String getAddress() { return address; }
    public int getNumberPassport() { return passport; }
    public BankAccount getBankAccount() { return bankAccount; }
    public int getPhone() { return Phone; }
    public int getId() { return id; }
    public boolean getAdress() { return !Objects.equals(address, fakeAdres); }
    public boolean getPassport() { return passport != fakePassport; }
    public BankAccount getAccount() { return bankAccount; }

    public void setMoney(double sum, LocalDateTime dateTime) { bankAccount.topUpYourAccount(sum, dateTime); }
}
