package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.zipcodewilmington.casino.cards.Card;
import com.github.zipcodewilmington.casino.cards.Rank;

public class BlackjackHand {
    private List<Card> hand;
    private static final Map<Rank, Integer> handValue = new HashMap<>();
    
    static {
        handValue.put(Rank.ACE, 11);
        handValue.put(Rank.TWO, 2);
        handValue.put(Rank.THREE, 3);
        handValue.put(Rank.FOUR, 4);
        handValue.put(Rank.FIVE, 5);
        handValue.put(Rank.SIX, 6);
        handValue.put(Rank.SEVEN, 7);
        handValue.put(Rank.EIGHT, 8);
        handValue.put(Rank.NINE, 9);
        handValue.put(Rank.TEN, 10);
        handValue.put(Rank.JACK, 10);
        handValue.put(Rank.QUEEN, 10);
        handValue.put(Rank.KING, 10);
    }
    public BlackjackHand() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public String toString() {
        return hand.toString();
    }

    public int getHandValue() {
        int total = 0;
        int ace = 0;
        for(Card card : hand) {
            total += handValue.get(card.getRank());
            if(card.getRank() == Rank.ACE) {
                ace++;
            }
        }
        while(total > 21 && ace >0) {
            total -=10;
            ace--;
        }
        return total;
    }

    public void clearHand() {
        hand.clear();
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public Card showFirstCard() {
        return hand.get(0);
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() ==21;
    }

    public int getFirstCardValue() {
        return handValue.get(hand.get(0).getRank()); 
    }

}
