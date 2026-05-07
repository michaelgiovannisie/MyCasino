package com.github.zipcodewilmington.casino.games.blackjack;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class BlackjackPlayer implements PlayerInterface {
    private CasinoAccount casinoAccount;
    private BlackjackHand hand;

    public BlackjackPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        hand = new BlackjackHand();
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    public BlackjackHand getHand() {
        return hand;
    }

}
