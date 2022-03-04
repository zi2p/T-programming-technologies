package com.company.lab1.banksTest;

import com.company.lab1.banks.dataTime.DataTime;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BanksTest {
    private CentralBank CentralBank;

    @BeforeAll
    public void setup() { CentralBank = new CentralBank(); }

    @Test
    public void addBankToCentralBankList_CentralBankListContainsBank() {
        var bank = new Bank(0);
        CentralBank.addBank(bank);
        assertTrue(CentralBank.getListBanks().contains(bank));
    }

    @Test
    public void theMoneyWasReturnedToTheClientAfterTheTransactionWasCanceled() {
        double sum1 = 100000.5;
        double sum2 = 100000.3;
        var ClientBuilder = new ClientBuilder();
        ClientBuilder.setName("Ксения Павловна", 1613923365);
        BankAccount bankAccount1 = new Debit(sum1);
        var transferLimit = new TransferLimit(sum1 / 2);
        var bank = new Bank(0);
        ClientBuilder.setAccount(bankAccount1);
        ClientBuilder.setAddress("ул.Михалкова 14");
        ClientBuilder.setPassport(1613923365);
        Client Ksenia = ClientBuilder.Build();
        MethodPercentageChange methodPercentageChange = new PercentageChange();
        bank.setMethodPercentageChange(methodPercentageChange);
        bank.setMethodTransferLimit(transferLimit);

        var ClientBuilder2 = new ClientBuilder();
        ClientBuilder2.setName("Лидия Михайловна", 1414778398);
        BankAccount bankAccount2 = new Debit(sum2);
        ClientBuilder2.setAccount(bankAccount2);
        ClientBuilder2.setAddress("ул.Чуйкова 5");
        ClientBuilder2.setPassport(1414778398);
        Client Lida = ClientBuilder.Build();
        bankAccount1.setMyBank(bank);
        bankAccount2.setMyBank(bank);
        CentralBank.addBank(bank);
        bank.setMyCentralBank(CentralBank);
        assertEquals(sum1, bankAccount1.getTheAmountOnTheAccount());
        bankAccount1.transferOfMoney((sum1 / 4), Lida, new DataTime());
    }
}
