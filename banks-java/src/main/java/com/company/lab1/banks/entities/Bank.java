package com.company.lab1.banks.entities;

import com.company.lab1.banks.entities.banksAccounts.BankAccount;
import com.company.lab1.banks.entities.client.Client;
import com.company.lab1.banks.entities.methods.TransferLimit;
import com.company.lab1.banks.entities.methods.percentage.MethodPercentageChange;
import com.company.lab1.banks.services.CentralBank;
import org.javatuples.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank implements Banks {

    private CentralBank myCentralBank;
    private int id;
    private int clientID;
    private TransferLimit transferLimit;
    private TransferLimit doubtfulLimit;
    private MethodPercentageChange percentageChange;
    private ArrayList<Pair<Client, BankAccount>> clients;

    public Bank(int id) {
        this.id = id;
        clients = new ArrayList<Pair<Client, BankAccount>>();
        clientID = 0;
        transferLimit = new TransferLimit(Double.MAX_VALUE);
    }

    private void setTransferLimit(TransferLimit transferLimit) { this.transferLimit = transferLimit; }
    private void setPercentageChange(MethodPercentageChange method) { percentageChange = method; }
    private void setClientID(int id) { clientID = id;}
    private Client setClient(String name, String address, int passport) {
        return new Client(name, clientID, address, passport);
    }

    private int getClientID() { return clientID; }
    private ArrayList<Pair<Client, BankAccount>> getClients() { return clients; }
    public MethodPercentageChange getPercentageChange(){ return percentageChange; }

    public void setMethodPercentageChange(MethodPercentageChange percentageChange) { this.percentageChange = percentageChange; }
    public void setMethodTransferLimit(TransferLimit transferLimit) { this.transferLimit = transferLimit; }
    public void setDoubtfulLimit(TransferLimit transferLimit) { doubtfulLimit = transferLimit; }
    public void setMyCentralBank(CentralBank cb) { myCentralBank = cb; }

    public int getId() { return id; }
    public TransferLimit getDoubtfulLimit() { return doubtfulLimit; }
    public TransferLimit getTransferLimit() { return transferLimit; }
    public CentralBank getMyCentralBank() { return myCentralBank; }

    public Client addClient(String name, BankAccount account, String address, int passport) {
        Pair<Client, BankAccount> client = new Pair<Client, BankAccount>(setClient(name, address, passport), account);
        clients.add(client);
        clientID++;
        return client.getValue0();
    }
    public void addClient(Client person) {
        var client = new Pair<Client, BankAccount>(person, person.getAccount());
        clients.add(client);
    }

    public double getMoney(Client person, double sum, LocalDateTime dateTime) {
        for (Pair<Client, BankAccount> client : clients) {
            if (client.getValue0().getId() == person.getId()) {
                if (client.getValue0().getAdress() && client.getValue0().getPassport()) {return client.getValue1().cashWithdrawal(sum, dateTime);}
                return client.getValue1().cashWithdrawal(Math.min(sum, doubtfulLimit.getMaxSum()), dateTime);
            }
        }
        return 0;
    }
}
