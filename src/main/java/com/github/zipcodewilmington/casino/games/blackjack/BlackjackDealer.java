package com.github.zipcodewilmington.casino.games.blackjack;

public class BlackjackDealer {
    private BlackjackHand hand;

    public BlackjackDealer() {
        hand = new BlackjackHand();
    }

    public BlackjackHand getHand() {
        return hand;
    }
}
