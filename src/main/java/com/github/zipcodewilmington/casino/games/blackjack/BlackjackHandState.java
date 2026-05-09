package com.github.zipcodewilmington.casino.games.blackjack;

public class BlackjackHandState {
    private BlackjackHand hand;
    private int bet;
    private boolean doubleDown;
    private boolean surrendered;
    private boolean finished;
    private boolean splitAces;

    public BlackjackHandState(int bet) {
        this.hand = new BlackjackHand();
        this.bet = bet;
        this.doubleDown = false;
        this.surrendered = false;
        this.finished = false;
        this.splitAces = false;
    }

    public BlackjackHand getHand() {
        return this.hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void doubleBet() {
        this.bet *= 2;
        this.doubleDown = true;
    }
    
    public boolean isDoubleDown() {
        return doubleDown;
    }

    public void markDoubleDown() {
        this.doubleDown = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public void markFinished() {
        this.finished = true;
    }

}
