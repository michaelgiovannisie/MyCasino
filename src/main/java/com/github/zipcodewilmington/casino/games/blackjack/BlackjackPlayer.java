package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class BlackjackPlayer implements PlayerInterface {
    private CasinoAccount casinoAccount;
    private List<BlackjackHandState> handStates;

    public BlackjackPlayer(CasinoAccount casinoAccount) {
        this.casinoAccount = casinoAccount;
        handStates = new ArrayList<>();
    }

    @Override
    public CasinoAccount getArcadeAccount() {
        return casinoAccount;
    }

    public List<BlackjackHandState> getHandStates() {
        return handStates;
    }
}
