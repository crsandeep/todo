package com.codepath.doit.models;

import android.graphics.Color;

public enum Priority {
    LOW("Low", 2, Color.rgb(204, 214, 0)),
    MEDIUM("Medium", 1, Color.rgb(255, 170, 0)),
    HIGH("High", 0, Color.rgb(255, 0, 0));

    private final int value;
    private final String name;
    private final int color;

    Priority(final String newName, final int newValue, final int newColor) {
        name = newName;
        value = newValue;
        color = newColor;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    @Override public String toString(){
        return name;
    }
}