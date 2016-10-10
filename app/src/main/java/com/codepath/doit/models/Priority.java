package com.codepath.doit.models;

import android.graphics.Color;

import com.codepath.doit.activities.MainActivity;

public enum Priority {
    LOW("low", 2, Color.rgb(204, 214, 0)),
    MEDIUM("medium", 1, Color.rgb(255, 170, 0)),
    HIGH("high", 0, Color.rgb(255, 0, 0));

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
        return MainActivity.getContext().getString(MainActivity.getContext().getResources().getIdentifier(name, "string",  MainActivity.getContext().getPackageName()));
    }

    public int getColor() {
        return color;
    }

    @Override public String toString(){
        return MainActivity.getContext().getString(MainActivity.getContext().getResources().getIdentifier(name, "string",  MainActivity.getContext().getPackageName()));
    }
}