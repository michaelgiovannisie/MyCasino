package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.casino.cards.Deck;
import com.github.zipcodewilmington.utils.IOConsole;

public class BlackjackGame implements GameInterface{
    private List<PlayerInterface> players;
    private Deck deck;
    private BlackjackDealer dealer;
    private IOConsole input;
    private int bet;

    public BlackjackGame() {
        players = new ArrayList<>();
        dealer = new BlackjackDealer();
        deck = new Deck();
        deck.shuffle();
        input  = new IOConsole();
    }

    public void run() {
        if (players.isEmpty()) {
            System.out.println("You don't have enough balance.");
            return;
        }
        printGameStart();
        while (true) { 
            BlackjackPlayer player = (BlackjackPlayer) players.get(0);
            CasinoAccount account = player.getArcadeAccount();
            System.out.println("Your current balance: " + account.getAccountBalance());
            while(true) {
                bet = input.getIntegerInput("Enter your bet: ");
                if (bet <= 0) {
                    System.out.println("Bet must be greater than 0.");
                    continue;
                }
                if (bet > account.getAccountBalance()) {
                    System.out.println("Insufficient balance.");
                    continue;
                }
                break; 
            }
            account.withdraw(bet);
            player.getHand().clearHand();
            dealer.getHand().clearHand();
            player.getHand().addCard(deck.drawCard());
            player.getHand().addCard(deck.drawCard());
            dealer.getHand().addCard(deck.drawCard());
            dealer.getHand().addCard(deck.drawCard());
            System.out.println("Dealer: " + dealer.getHand().getFirstCardValue() + " | " + dealer.getHand().showFirstCard().toString());
            System.out.println("You: " + player.getHand().getHandValue() + " | " + player.getHand().toString());



            if(player.getArcadeAccount().getAccountBalance() <= 0) {
                System.out.println("You are out of money! Game over.");
                return;
            }

            while (true) {
                String playAgain = input.getStringInput("Do you want to play again? (Y/N)");

                if (playAgain.equalsIgnoreCase("N") || playAgain.equalsIgnoreCase("NO")) {
                    return;
                } else if (playAgain.equalsIgnoreCase("Y") || playAgain.equalsIgnoreCase("YES")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        }
    }

    public void printGameStart() {
        System.out.println("Welcome to the Blackjack Game!");
    }

    @Override
    public void add(PlayerInterface player) {
        if(player.getArcadeAccount().getAccountBalance() > 0) {
            players.add(player);
        }
    }

    @Override
    public void remove(PlayerInterface player) {
        players.remove(player);
    }

}
