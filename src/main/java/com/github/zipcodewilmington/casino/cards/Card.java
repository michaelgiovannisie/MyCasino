package com.github.zipcodewilmington.casino.cards;

public class Card {

    private Rank rank;
    private Suit suit;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Rank getRank() {
        return this.rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    @Override
public String toString() {
    String rankDisplay = "";
    switch (rank) {
        case ACE:
            rankDisplay = "A";
            break;
        case KING:
            rankDisplay = "K";
            break;
        case QUEEN:
            rankDisplay = "Q";
            break;
        case JACK:
            rankDisplay = "J";
            break;
        case TEN:
            rankDisplay = "10";
            break;
        case NINE:
            rankDisplay = "9";
            break;
        case EIGHT:
            rankDisplay = "8";
            break;
        case SEVEN:
            rankDisplay = "7";
            break;
        case SIX:
            rankDisplay = "6";
            break;
        case FIVE:
            rankDisplay = "5";
            break;
        case FOUR:
            rankDisplay = "4";
            break;
        case THREE:
            rankDisplay = "3";
            break;
        case TWO:
            rankDisplay = "2";
            break;
    }
    String suitDisplay = "";
    switch (suit) {
        case HEARTS:
            suitDisplay = "❤️";
            break;
        case DIAMONDS:
            suitDisplay = "♦️";
            break;
        case CLUBS:
            suitDisplay = "♣️";
            break;
        case SPADES:
            suitDisplay = "♠️";
            break;
    }
    return rankDisplay + " " + suitDisplay;
}

}
