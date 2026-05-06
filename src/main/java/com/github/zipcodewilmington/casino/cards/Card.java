package com.github.zipcodewilmington.casino.cards;

public class Card {

    private Rank rank;
    private Suit suit;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return suit + " of " + rank;
    }

}
