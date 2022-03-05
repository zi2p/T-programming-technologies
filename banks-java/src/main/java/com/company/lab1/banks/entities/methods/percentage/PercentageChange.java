package com.company.lab1.banks.entities.methods.percentage;
import org.javatuples.Triplet;
import java.util.ArrayList;

public class PercentageChange implements MethodPercentageChange {
    private ArrayList<Triplet<Integer, Integer, Double>> table = new ArrayList<Triplet<Integer, Integer, Double>>();

    public PercentageChange() { table = new ArrayList<Triplet<Integer, Integer, Double>>(); }

    public ArrayList<Triplet<Integer, Integer, Double>> getTable() { return table; }

    public void addСondition(int start, int finish, double proc) {
        Triplet<Integer, Integer, Double> tuple = new Triplet<Integer, Integer, Double>(start, finish, proc);
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getValue0() <= start || table.get(i).getValue0() >= finish) { continue; }
            Triplet<Integer, Integer, Double> newTuple = new Triplet<Integer, Integer, Double>(finish, table.get(i).getValue1(), table.get(i).getValue2());
            table.remove(table.get(i));
            table.add(newTuple);
        }
        table.add(tuple);
    }

    public double getPercentage(double sum) {
        for (Triplet<Integer, Integer, Double> objects : table) {
            if (objects.getValue0() < sum && objects.getValue1() > sum) { return sum * objects.getValue2(); }
        }
        return sum;
    }
}
