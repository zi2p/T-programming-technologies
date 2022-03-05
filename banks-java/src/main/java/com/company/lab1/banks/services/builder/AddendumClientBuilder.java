package com.company.lab1.banks.services.builder;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;

public class AddendumClientBuilder implements ClientBuilders{
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;
    private String fakeAdres = " ";
    private int fakePassport = 0;
    private Client client;

    public AddendumClientBuilder(Client client) { this.client = client; }

    public Client Build() { return client; }
    public void setName(String name, int id) { }
    public void setAddress(String address) { client.address = address; }
    public void setAccount(BankAccount account) { client.bankAccount = account; }
    public void setPhone(int phone) { client.Phone = phone; }
    public void setPassport(int passport) {
        if (passport <= MAXPASSPORT && passport >= MINPASSPORT) client.passport = passport;
    }
}
