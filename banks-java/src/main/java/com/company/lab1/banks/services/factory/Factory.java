package com.company.lab1.banks.services.factory;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;

public interface Factory {
    public void setAccount(BankAccount account);
    public BankAccount createdBankAccount(int account);
}
