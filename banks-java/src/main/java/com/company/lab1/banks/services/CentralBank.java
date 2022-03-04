package com.company.lab1.banks.services;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.Bank;
import com.company.lab1.banks.entities.Banks;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;

import java.util.ArrayList;

public class CentralBank {
    /* Регистрацией всех банков, а также взаимодействием между банками занимается центральный банк.
         Он должен предоставлять все нужные другим банкам методы, методы добавления нового банка.
         Он также занимается уведомлением других банков о том, что нужно начислять остаток или комиссию -
         для этого механизма НЕ требуется создавать таймеры и завязываться на реальное время. */
    public CentralBank()
    {
        Banks = new ArrayList<Bank>();
        id = 0;
    }

    private int id;
    private ArrayList<Bank> Banks;
    private ArrayList<Bank> getBank() { return Banks; }

    public void addBank(MethodPercentageChange percentageChange, TransferLimit transferLimit) {
        var bank = new Bank(id);
        id++;
        provideTheMethodToTheBank(bank, percentageChange, transferLimit);
        Banks.add(bank);
        bank.setMyCentralBank(this);
    }

    public void addBank(Bank bank){ Banks.add(bank); }
    public ArrayList<Bank> getListBanks() { return Banks; }
    public void provideTheMethodToTheBank(Banks bank, MethodPercentageChange percentageChange, TransferLimit transferLimit) {
        bank.setMethodPercentageChange(percentageChange);
        bank.setMethodTransferLimit(transferLimit);
    }

    public boolean monthPassed(DataTime lastTime, DataTime nowTime) {
        return (nowTime.NOWMONTH - lastTime.NOWMONTH == 1 && nowTime.NOWYEAR == lastTime.NOWYEAR) || (nowTime.NOWYEAR == lastTime.NOWYEAR + 1 && nowTime.NOWMONTH == 1 && lastTime.NOWMONTH == 12);
    }
    public boolean dayPassed(DataTime lastTime, DataTime nowTime) {
        return nowTime.NOWYEAR == lastTime.NOWYEAR && nowTime.NOWMONTH == lastTime.NOWMONTH &&
                nowTime.NOWDAY == lastTime.NOWDAY + 1;
    }
}