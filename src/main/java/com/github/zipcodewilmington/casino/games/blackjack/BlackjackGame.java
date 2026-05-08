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
    private boolean playerTurnOver;
    private boolean roundOver;
    private double winnings;

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
            playerTurnOver = false;
            roundOver = false;
            winnings = 0;
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

            if(player.getHand().hasBlackjack() && !dealer.getHand().hasBlackjack()) {
                winnings = bet + bet * 1.2;
                account.deposit(winnings);
                System.out.println("You won " + winnings + " !");
                System.out.println("Your current balance: " + player.getArcadeAccount().getAccountBalance());
                roundOver = true;
            } else if(!player.getHand().hasBlackjack() && dealer.getHand().hasBlackjack()) {
                System.out.println("You lost.");
                roundOver = true;
            } else if(player.getHand().hasBlackjack() && dealer.getHand().hasBlackjack()) {
                account.deposit(bet);
                System.out.println("Push.");
                roundOver = true;
            }

            if(!roundOver) {
                while (!playerTurnOver && !player.getHand().isBust()) {
                    String move = input.getStringInput("Hit/Stand?");
                    if (move.toUpperCase().equals("HIT") || move.toUpperCase().equals("H")) {
                        player.getHand().addCard(deck.drawCard());
                        System.out.println("Dealer: " + dealer.getHand().getFirstCardValue() + " | " + dealer.getHand().showFirstCard().toString());
                        System.out.println("You: " + player.getHand().getHandValue() + " | " + player.getHand().toString());
                        if (player.getHand().isBust()) {
                            System.out.println("Bust! You lose.");
                            roundOver = true;
                            playerTurnOver = true;
                        }
                    } else if (move.toUpperCase().equals("STAND") || move.toUpperCase().equals("S")) {
                        playerTurnOver = true;
                        break;
                    } else {
                        System.out.println("Invalid input.");
                        continue;
                    }
                }
            }

            if(!roundOver){
                while(dealer.getHand().getHandValue() < 18 || (dealer.getHand().getHandValue() == 18 && dealer.getHand().isSoft())) {
                    dealer.getHand().addCard(deck.drawCard());
                    System.out.println("Dealer: " + dealer.getHand().getHandValue() + " | " + dealer.getHand().toString());
                    System.out.println("You: " + player.getHand().getHandValue() + " | " + player.getHand().toString());
                }
            }

            if(!roundOver) {
                if(dealer.getHand().isBust()) {
                    winnings = bet * 2;
                    account.deposit(winnings);
                    System.out.println("You won " + winnings + " !");
                    System.out.println("Your current balance: " + player.getArcadeAccount().getAccountBalance());
                    roundOver = true;
                } else if(player.getHand().getHandValue() > dealer.getHand().getHandValue()) {
                    winnings = bet * 2;
                    account.deposit(winnings);
                    System.out.println("You won " + winnings + " !");
                    System.out.println("Your current balance: " + player.getArcadeAccount().getAccountBalance());
                    roundOver = true;
                } else if (player.getHand().getHandValue() < dealer.getHand().getHandValue()) {
                    System.out.println("You lost.");
                    roundOver = true;
                } else {
                    System.out.println("Push.");
                    account.deposit(bet);
                    roundOver = true;
                }
            }

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
