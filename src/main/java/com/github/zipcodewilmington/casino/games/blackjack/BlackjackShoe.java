package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.zipcodewilmington.casino.cards.Card;
import com.github.zipcodewilmington.casino.cards.Rank;
import com.github.zipcodewilmington.casino.cards.Suit;

public class BlackjackShoe {
    private List<Card> cards;
    private int numberOfDecks;
    private int reshuffleThreshold;
    
    public BlackjackShoe() {
        cards = new ArrayList<>();
        numberOfDecks = 6;
        reshuffleThreshold = 110;
        for(int i = 0; i < 6; i++) {
            for(Suit suit : Suit.values()) {
                for(Rank rank : Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean needsReshuffle() {
        return cards.size() < reshuffleThreshold;
    }
}
