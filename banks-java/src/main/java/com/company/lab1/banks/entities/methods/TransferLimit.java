package com.company.lab1.banks.entities.methods;

public class TransferLimit implements MethodTransferLimit {
    private double sum;

    public TransferLimit(double sum){
        this.sum = sum;
    }

    private double getSum() {
        return sum;
    }
    public double getMaxSum(){
        return sum;
    }
}
