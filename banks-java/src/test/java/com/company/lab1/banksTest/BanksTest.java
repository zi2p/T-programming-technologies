package com.company.lab1.banksTest;


import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.banksAccounts.Debit;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.entities.methods.percentage.PercentageChange;
import com.company.lab1.banks.services.CentralBank;
import com.company.lab1.banks.services.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BanksTest {
    private CentralBank centralBank;

    @BeforeAll
    public void setup() { centralBank = new CentralBank(); }

    @Test
    public void addBankToCentralBankList_CentralBankListContainsBank() {
        var bank = new Bank(0);
        centralBank.addBank(bank);
        assertTrue(centralBank.getListBanks().contains(bank));
    }

    @Test
    public void theMoneyWasReturnedToTheClientAfterTheTransactionWasCanceled() {
        double sum1 = 100000.5;
        double sum2 = 100000.3;
        var clientBuilder = new ClientBuilder();
        clientBuilder.setName("Ксения Павловна", 1613923365);
        BankAccount bankAccount1 = new Debit(sum1);
        var transferLimit = new TransferLimit(sum1 / 2);
        var bank = new Bank(0);
        clientBuilder.setAccount(bankAccount1);
        clientBuilder.setAddress("ул.Михалкова 14");
        clientBuilder.setPassport(1613923365);
        Client Ksenia = clientBuilder.Build();
        MethodPercentageChange methodPercentageChange = new PercentageChange();
        bank.setMethodPercentageChange(methodPercentageChange);
        bank.setMethodTransferLimit(transferLimit);

        var clientBuilder2 = new ClientBuilder();
        clientBuilder2.setName("Лидия Михайловна", 1414778398);
        BankAccount bankAccount2 = new Debit(sum2);
        clientBuilder2.setAccount(bankAccount2);
        clientBuilder2.setAddress("ул.Чуйкова 5");
        clientBuilder2.setPassport(1414778398);
        Client Lida = clientBuilder.Build();
        bankAccount1.setMyBank(bank);
        bankAccount2.setMyBank(bank);
        centralBank.addBank(bank);
        bank.setMyCentralBank(centralBank);
        assertEquals(sum1, bankAccount1.getTheAmountOnTheAccount());
        bankAccount1.transferOfMoney((sum1 / 4), Lida, LocalDateTime.now());
    }
}
