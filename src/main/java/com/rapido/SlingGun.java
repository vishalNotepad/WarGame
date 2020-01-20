package com.rapido;

public class SlingGun extends Battalion {
    int count;

    public SlingGun(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
