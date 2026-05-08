package com.github.zipcodewilmington.casino.games.blackjack;

public class BlackjackHandScore {
    private final int value;
    private final boolean soft;

    public BlackjackHandScore(int value, boolean soft) {
        this.value = value;
        this.soft = soft;
    }

    public int getValue() {
        return value;
    }

    public boolean isSoft() {
        return soft;
    }
}
