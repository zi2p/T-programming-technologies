package com.company.lab1.banks.entities.methods.percentage;
import org.javatuples.Triplet;
import java.util.ArrayList;

public class PercentageChange implements MethodPercentageChange {
    public ArrayList<Triplet<Integer, Integer, Double>> Table = new ArrayList<Triplet<Integer, Integer, Double>>();
    public PercentageChange() { Table = new ArrayList<Triplet<Integer, Integer, Double>>(); }
    public ArrayList<Triplet<Integer, Integer, Double>> getTable() { return Table; }

    public void addСondition(int start, int finish, double proc) {
        Triplet<Integer, Integer, Double> tuple = new Triplet<Integer, Integer, Double>(start, finish, proc);
        for (int i=0; i < Table.size(); i++) {
            if (Table.get(i).getValue0() <= start || Table.get(i).getValue0() >= finish) continue;
            Triplet<Integer, Integer, Double> newTuple = new Triplet<Integer, Integer, Double>(finish, Table.get(i).getValue1(), Table.get(i).getValue2());
            Table.remove(Table.get(i));
            Table.add(newTuple);
        }
        Table.add(tuple);
    }

    public double getPercentage(double sum) {
        for (Triplet<Integer, Integer, Double> objects : Table) {
            if (objects.getValue0() < sum && objects.getValue1() > sum) return sum * objects.getValue2();
        }
        return sum;
    }
}
