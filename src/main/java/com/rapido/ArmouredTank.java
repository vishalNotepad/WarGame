package com.rapido;

public class ArmouredTank extends Battalion {
    int count;

    public ArmouredTank(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
