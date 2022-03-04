package com.company.lab1.banks.entities;

import com.company.lab1.banks.dataTime.DataTime;
import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.services.CentralBank;
import org.javatuples.Pair;

import java.util.ArrayList;

public class Bank implements Banks {
    public Bank(int id)
    {
        ID = id;
        Clients = new ArrayList<Pair<Client, BankAccount>>();
        ClientID = 0;
        TransferLimit = new TransferLimit(Double.MAX_VALUE);
    }
    private CentralBank MyCentralBank;
    private int ID;
    private int ClientID;
    private TransferLimit TransferLimit;
    private TransferLimit DoubtfulLimit;
    private MethodPercentageChange PercentageChange;
    private ArrayList<Pair<Client, BankAccount>> Clients;


    private void setTransferLimit(TransferLimit transferLimit) { TransferLimit = transferLimit; }
    private void setPercentageChange(MethodPercentageChange method) { PercentageChange = method; }
    private void setClientID(int id) { ClientID = id;}
    private Client setClient(String name, String address, int passport) {
        return new Client(name, ClientID, address, passport);
    }

    private int getClientID() { return ClientID; }
    private ArrayList<Pair<Client, BankAccount>> getClients() { return Clients; }
    public MethodPercentageChange getPercentageChange(){ return PercentageChange; }

    public void setMethodPercentageChange(MethodPercentageChange percentageChange) { PercentageChange = percentageChange; }
    public void setMethodTransferLimit(TransferLimit transferLimit) { TransferLimit = transferLimit; }
    public void setDoubtfulLimit(TransferLimit transferLimit) { DoubtfulLimit = transferLimit; }
    public void setMyCentralBank(CentralBank cb) { MyCentralBank = cb; }

    public int getID() { return ID; }
    public TransferLimit getDoubtfulLimit() { return DoubtfulLimit; }
    public TransferLimit getTransferLimit() { return TransferLimit; }
    public CentralBank getMyCentralBank() { return MyCentralBank; }

    public Client addClient(String name, BankAccount account, String address, int passport) {
        Pair<Client, BankAccount> client = new Pair<Client, BankAccount>(setClient(name, address, passport), account);
        Clients.add(client);
        ClientID++;
        return client.getValue0();
    }
    public void addClient(Client person) {
        var t = new Pair<Client, BankAccount>(person, person.getAccount());
        Clients.add(t);
    }

    public double getMoney(Client person, double sum, DataTime dateTime) {
        for (Pair<Client, BankAccount> client : Clients) {
            if (client.getValue0().getID() == person.getID()) {
                if (client.getValue0().getAdress() && client.getValue0().getPassport()) return client.getValue1().cashWithdrawal(sum, dateTime);
                return client.getValue1().cashWithdrawal(sum < DoubtfulLimit.getMaxSum() ? sum : DoubtfulLimit.getMaxSum(), dateTime);
            }
        }
        return 0;
    }
}
