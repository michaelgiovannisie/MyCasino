package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.casino.cards.Card;

public class BlackjackPlayer implements PlayerInterface {
    private CasinoAccount casinoAccount;
    private List<Card> hand;

    public BlackjackPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        hand = new ArrayList<>();
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public String toString() {
        return hand.toString();
    }

    public void getHandValue() {
        for(List card : hand) {

        }
    }

}
