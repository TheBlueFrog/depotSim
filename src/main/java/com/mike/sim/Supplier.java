package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends LocatedObject{

    public Supplier(Location location) {
        super(location);
    }

    static List<Supplier> initSuppliers(int scenario) {
        List<Supplier> suppliers = new ArrayList<>();
        switch (scenario) {
            case 0:
                suppliers.add(
                        new Supplier(
                                new Location(-7, 0)));
                break;
            case 1:
            case 2:
                suppliers.add(
                        new Supplier(
                                new Location(-7, 0)));
                suppliers.add(
                        new Supplier(
                                new Location(-9, 0)));
                break;
        }
        return suppliers;
    }
}
