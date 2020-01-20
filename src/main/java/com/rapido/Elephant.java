package com.rapido;

public class Elephant extends Battalion {
    int count;

    public Elephant(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
