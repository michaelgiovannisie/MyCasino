package com.github.zipcodewilmington.casino.games.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.utils.IOConsole;

public class BlackjackGame implements GameInterface{
    private List<PlayerInterface> players;
    private BlackjackShoe shoe;
    private BlackjackDealer dealer;
    private IOConsole input;
    private int bet;
    private boolean playerTurnOver;
    private boolean roundOver;
    private double winnings;
    private boolean canDoubleDown;
    private boolean hasInsurance;
    private int insuranceBet;

    public BlackjackGame() {
        players = new ArrayList<>();
        dealer = new BlackjackDealer();
        shoe = new BlackjackShoe();
        shoe.shuffle();
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
            canDoubleDown = true;
            hasInsurance = false;
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
            player.getHandStates().clear();
            player.getHandStates().add(new BlackjackHandState(bet));
            BlackjackHandState currentState = player.getHandStates().get(0);
            BlackjackHand currentHand = currentState.getHand();
            currentHand.clearHand();
            dealer.getHand().clearHand();
            currentHand.addCard(shoe.drawCard());
            currentHand.addCard(shoe.drawCard());
            dealer.getHand().addCard(shoe.drawCard());
            dealer.getHand().addCard(shoe.drawCard());
            System.out.println("Dealer: " + dealer.getHand().getFirstCardValue() + " | " + dealer.getHand().showFirstCard().toString());
            System.out.println("You: " + currentHand.getHandValue() + " | " + currentHand.toString());
            if(dealer.getHand().getFirstCardValue() == 11 && player.getArcadeAccount().getAccountBalance() > 0) {
                while(true) {
                    String insurance = input.getStringInput("Do you want to insure? (Y/N)");
                     if (insurance.equalsIgnoreCase("N") || insurance.equalsIgnoreCase("NO")) {
                        break;
                    } else if (insurance.equalsIgnoreCase("Y") || insurance.equalsIgnoreCase("YES")) {
                        while(true) {
                            insuranceBet = input.getIntegerInput("Enter your insurance bet: ");
                            if (insuranceBet <= 0 || insuranceBet > bet/2 || insuranceBet > player.getArcadeAccount().getAccountBalance()) {
                                System.out.println("Bet must be greater than 0 and less than equal to " + bet/2);
                                continue;
                            }
                            account.withdraw(insuranceBet);
                            hasInsurance = true;
                            break;
                        }
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter Y or N.");
                    }
                }
            }
            if(currentHand.hasBlackjack() && !dealer.getHand().hasBlackjack()) {
                winnings = currentState.getBet() + (currentState.getBet() * 1.2);
                account.deposit(winnings);
                System.out.println("BLACKJACK! You won " + winnings + " !");
                System.out.println("Your current balance: " + player.getArcadeAccount().getAccountBalance());
                roundOver = true;
            } else if(!currentHand.hasBlackjack() && dealer.getHand().hasBlackjack()) {
                System.out.println("Dealer: " + dealer.getHand().getHandValue() + " | " + dealer.getHand().toString());
                System.out.println("You: " + currentHand.getHandValue() + " | " + currentHand.toString());
                if(hasInsurance) {
                    insuranceBet *= 3;
                    account.deposit(insuranceBet);
                    System.out.println("You are insured");
                } else {
                System.out.println("You lost.");
                }
                roundOver = true;
            } else if(currentHand.hasBlackjack() && dealer.getHand().hasBlackjack()) {
                account.deposit(currentState.getBet());
                System.out.println("Push.");
                roundOver = true;
            }

            if(currentHand.canSplit() && account.getAccountBalance() >= bet) {
                while(true) {
                    String askSplit = input.getStringInput("Do you want to split?");
                    if (askSplit.equalsIgnoreCase("N") || askSplit.equalsIgnoreCase("NO")) {
                        break;
                    } else if (askSplit.equalsIgnoreCase("Y") || askSplit.equalsIgnoreCase("YES")) {
                        account.withdraw(bet);
                        BlackjackHandState secondState = new BlackjackHandState(currentState.getBet());
                        player.getHandStates().add(secondState);
                        BlackjackHand secondHand = secondState.getHand();
                        secondHand.addCard(currentHand.removeCard(1));
                        currentHand.addCard(shoe.drawCard());
                        secondHand.addCard(shoe.drawCard());
                        System.err.println(currentHand.toString());
                        System.err.println(secondHand.toString());
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter Y or N.");
                    }
                }
            }

            if(!roundOver) {
                for(BlackjackHandState state : player.getHandStates()) {
                    BlackjackHand playerHand = state.getHand();
                    playerTurnOver = false;
                    canDoubleDown = true;
                    while (!playerTurnOver && !state.getHand().isBust()) {
                        String move = input.getStringInput("Hit | Stand | Double down");
                        if (move.toUpperCase().equals("HIT") || move.toUpperCase().equals("H")) {
                            canDoubleDown = false;
                            playerHand.addCard(shoe.drawCard());
                            System.out.println("Dealer: " + dealer.getHand().getFirstCardValue() + " | " + dealer.getHand().showFirstCard().toString());
                            System.out.println("You: " + state.getHand().getHandValue() + " | " + state.getHand().toString());
                            if (state.getHand().isBust()) {
                                System.out.println("Bust! You lose.");
                                playerTurnOver = true;
                            } else if (state.getHand().getHandValue() == 21) {
                                playerTurnOver = true;
                            }
                        } else if (move.toUpperCase().equals("STAND") || move.toUpperCase().equals("S")) {
                            playerTurnOver = true;
                            break;
                        } else if((move.toUpperCase().equals("DOUBLE DOWN") || move.toUpperCase().equals("DD") || move.toUpperCase().equals("D")) && canDoubleDown) {
                            if (player.getArcadeAccount().getAccountBalance() < state.getBet()) {
                                System.out.println("Not enough balance to double down.");
                                continue;
                            }
                            account.withdraw(state.getBet());
                            state.doubleBet();
                            playerHand.addCard(shoe.drawCard());
                            System.out.println("Dealer: " + dealer.getHand().getFirstCardValue() + " | " + dealer.getHand().showFirstCard().toString());
                            System.out.println("You: " + state.getHand().getHandValue() + " | " + state.getHand().toString());
                            if(playerHand.isBust()) {
                                System.out.println("Bust! You lose.");
                            }
                            playerTurnOver = true;
                            break;
                        } else {
                            System.out.println("Invalid input.");
                            continue;
                        }
                    }
                }
            }

            if(!roundOver){
                while(dealer.getHand().getHandValue() < 17 || (dealer.getHand().getHandValue() == 17 && dealer.getHand().isSoft())) {
                    dealer.getHand().addCard(shoe.drawCard());
                    System.out.println("Dealer: " + dealer.getHand().getHandValue() + " | " + dealer.getHand().toString());
                    System.out.println("You: " + currentHand.getHandValue() + " | " + currentHand.toString());
                }
            }

            if (!roundOver) {
                for (BlackjackHandState state : player.getHandStates()) {
                    BlackjackHand hand = state.getHand();
                    int handBet = state.getBet();
                    if (hand.isBust()) {
                        System.out.println("Hand busted. You lose.");
                        continue;
                    }
                    if (dealer.getHand().isBust()) {
                        winnings = handBet * 2;
                        account.deposit(winnings);
                        System.out.println("You won " + winnings + " !");
                    } else if (hand.getHandValue() > dealer.getHand().getHandValue()) {
                        winnings = handBet * 2;
                        account.deposit(winnings);
                        System.out.println("You won " + winnings + " !");
                    } else if (hand.getHandValue() < dealer.getHand().getHandValue()) {
                        System.out.println("You lost.");
                    } else {
                        System.out.println("Push.");
                        account.deposit(handBet);
                    }
                }
                System.out.println("Your current balance: " + account.getAccountBalance());
                roundOver = true;
            }

            if(shoe.needsReshuffle()) {
                shoe = new BlackjackShoe();
                System.out.println("Shoe has been reshuffled.");
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
