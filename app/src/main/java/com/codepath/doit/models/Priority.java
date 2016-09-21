package com.codepath.doit.models;

public enum Priority {
    LOW("Low", 2),
    MEDIUM("Medium", 1),
    HIGH("High", 0);

    private final int value;
    private final String name;

    Priority(final String newName, final int newValue) {
        name = newName;
        value = newValue;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override public String toString(){
        return name;
    }
}