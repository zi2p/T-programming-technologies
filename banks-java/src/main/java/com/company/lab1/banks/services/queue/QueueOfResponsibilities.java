package com.company.lab1.banks.services.queue;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;

public class QueueOfResponsibilities implements Queue {
    public void bankingOperation(BankAccount account) {
        account.appointPercentages(account.getPercentages());
        account.addingInterestToTheAmount();
        account.appointСommission(account.getCommission());
    }
}
