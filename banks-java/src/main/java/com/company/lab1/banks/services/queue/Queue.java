package com.company.lab1.banks.services.queue;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;

public interface Queue {
    public void bankingOperation(BankAccount account);
}
