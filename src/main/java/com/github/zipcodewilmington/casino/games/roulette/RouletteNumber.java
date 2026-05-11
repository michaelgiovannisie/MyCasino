package com.github.zipcodewilmington.casino.games.roulette;

import com.github.zipcodewilmington.utils.AnsiColor;

public class RouletteNumber {
    private int value;
    private AnsiColor color;

    public RouletteNumber(int value, AnsiColor color) {
        
        if(value == 0 && color != AnsiColor.GREEN) {
            throw new IllegalArgumentException("Invalid roulette color.");
        } else if (value != 0 && color == AnsiColor.GREEN) {
            throw new IllegalArgumentException("Invalid roulette color.");
        } else if (color != AnsiColor.GREEN && color != AnsiColor.BLACK && color != AnsiColor.RED) {
            throw new IllegalArgumentException("Invalid roulette color.");
        } else {
            this.color = color;
        }
        if(value >= 0 && value <= 36) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid roulette number.");
        }
    }

    public int getValue() {
        return this.value;
    }

    public AnsiColor getAnsiColor() {
        return this.color;
    }

    public boolean isRed() {
        return color == AnsiColor.RED;
    }

    public boolean isBlack() {
        return color == AnsiColor.BLACK;
    }

    public boolean isGreen() {
        return color == AnsiColor.GREEN;
    }

    @Override
    public String toString() {
        return color.getColor() + value + AnsiColor.AUTO.getColor();
    }

}
