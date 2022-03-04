package com.company.lab1.banks.services.builder;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.tools.Exceptions;

public class ClientBuilder implements ClientBuilders {
    private final int MAXPASSPORT = 999999999;
    private final int MINPASSPORT = 1000000000;
    private String fakeAdres = " ";
    private int fakePassport = 0;

    private Client Client;

    public Client Build() {
        if (Client != null) return Client;
        throw new Exceptions("the client does not exist");
    }

    public void setName(String name, int id) { Client = new Client(name, id, fakeAdres, fakePassport); }
    public void setPassport(int passport) { if (passport <= MAXPASSPORT && passport >= MINPASSPORT) Client.Passport = passport; }
    public void setAddress(String address) { Client.Address = address; }
    public void setAccount(BankAccount account) { Client.BankAccount = account; }
}
