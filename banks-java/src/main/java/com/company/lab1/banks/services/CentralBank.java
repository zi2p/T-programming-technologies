package com.company.lab1.banks.services;

import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.Banks;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CentralBank {
    /* Регистрацией всех банков, а также взаимодействием между банками занимается центральный банк.
         Он должен предоставлять все нужные другим банкам методы, методы добавления нового банка.
         Он также занимается уведомлением других банков о том, что нужно начислять остаток или комиссию -
         для этого механизма НЕ требуется создавать таймеры и завязываться на реальное время. */
    private int id;
    private ArrayList<Bank> banks;

    public CentralBank() {
        banks = new ArrayList<Bank>();
        id = 0;
    }

    private ArrayList<Bank> getBank() { return banks; }
    public void addBank(MethodPercentageChange percentageChange, TransferLimit transferLimit) {
        var bank = new Bank(id);
        id++;
        provideTheMethodToTheBank(bank, percentageChange, transferLimit);
        banks.add(bank);
        bank.setMyCentralBank(this);
    }

    public void addBank(Bank bank){ banks.add(bank); }
    public ArrayList<Bank> getListBanks() { return banks; }
    public void provideTheMethodToTheBank(Banks bank, MethodPercentageChange percentageChange, TransferLimit transferLimit) {
        bank.setMethodPercentageChange(percentageChange);
        bank.setMethodTransferLimit(transferLimit);
    }

    public boolean monthPassed(LocalDateTime lastTime, LocalDateTime nowTime) {
        return (nowTime.getMonth().getValue() - lastTime.getMonth().getValue() == 1
                && nowTime.getYear() == lastTime.getYear())
                || (nowTime.getYear() == lastTime.getYear() + 1
                && nowTime.getMonth().getValue() == 1 && lastTime.getMonth().getValue() == 12);
    }
    public boolean dayPassed(LocalDateTime lastTime, LocalDateTime nowTime) {
        return nowTime.getYear() == lastTime.getYear()
                && nowTime.getMonth() == lastTime.getMonth()
                && nowTime.getDayOfMonth() == lastTime.getDayOfMonth() + 1;
    }
}