package com.rapido;

public class Horse extends Battalion {
    private int count;

    public Horse(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
