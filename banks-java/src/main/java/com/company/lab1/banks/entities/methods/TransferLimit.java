package com.company.lab1.banks.entities.methods;

public class TransferLimit implements MethodTransferLimit {
    public TransferLimit(double sum){
        Sum = sum;
    }
    private double Sum;
    private double getSum() {
        return Sum;
    }
    public double getMaxSum(){
        return Sum;
    }
}
