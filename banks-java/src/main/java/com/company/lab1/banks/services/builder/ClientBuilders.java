package com.company.lab1.banks.services.builder;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;

public interface ClientBuilders {
    public Client Build();
    public void setName(String name, int id);
    public void setPassport(int passport);
    public void setAddress(String address);
    public void setAccount(BankAccount account);
}
