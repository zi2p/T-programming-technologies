package com.company.lab1.banks.services.builder;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;

public class AddendumClientBuilder implements ClientBuilders{
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;
    private String fakeAdres = " ";
    private int fakePassport = 0;

    public AddendumClientBuilder(Client client) { Client = client; }
    private Client Client;

    public Client Build() { return Client; }
    public void setName(String name, int id) { }
    public void setAddress(String address) { Client.Address = address; }
    public void setAccount(BankAccount account) { Client.BankAccount = account; }
    public void setPhone(int phone) { Client.Phone = phone; }
    public void setPassport(int passport) {
        if (passport <= MAXPASSPORT && passport >= MINPASSPORT) Client.Passport = passport;
    }
}
