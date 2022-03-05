package com.company.lab1.banks.entities.client;


import com.company.lab1.banks.entities.banksAccounts.BankAccount;

import java.time.LocalDateTime;
import java.util.Objects;

public class Client {
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;
    private String fakeAdres = " ";
    private int fakePassport = 0;

    public Client(String name, int id, String address, int passport) {
        Name = name;
        Address = address;
        Passport = passport;
        ID = id;
        BankAccount = null;
    }
    public Client(String name, int id) {
        Name = name;
        ID = id;
        BankAccount = null;
    }
    public Client(String name, int id, int passport) {
        Name = name;
        Passport = passport;
        ID = id;
        BankAccount = null;
    }
    public Client(String name, int id, String address) {
        Name = name;
        Address = address;
        ID = id;
        BankAccount = null;
    }

    private String Name;
    private String getName() { return Name; }

    public String Address;
    public int Passport;
    public int Phone;
    public int ID;
    public BankAccount BankAccount;

    public void setPassport(int passport) { Passport=passport; }
    public void setPhone(int phone) { Phone=phone; }
    public void setAddress(String address) { Address=address; }
    public void setBankAccount(BankAccount bankAccount) { BankAccount=bankAccount; }

    public String getAddress() { return Address; }
    public int getNumberPassport() { return Passport; }
    public BankAccount getBankAccount() { return BankAccount; }
    public int getPhone() { return Phone; }
    public int getID() { return ID; }
    public boolean getAdress() { return !Objects.equals(Address, fakeAdres); }
    public boolean getPassport() { return Passport != fakePassport; }
    public BankAccount getAccount() { return BankAccount; }

    public void setMoney(double sum, LocalDateTime dateTime) { BankAccount.topUpYourAccount(sum, dateTime); }
}
