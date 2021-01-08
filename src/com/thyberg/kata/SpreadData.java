package com.thyberg.kata;

public class SpreadData {

    private final String name;
    private final int value1;
    private final int value2;

    SpreadData(String name, int value1, int value2) {
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
    }

    String getName() {
        return this.name;
    }

    int getSpread() {
        return Math.abs(this.value1 - this.value2);
    }

}