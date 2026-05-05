package com.github.zipcodewilmington.casino.games.numberguess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;

public class NumberGuessGame implements GameInterface {

    private int number;
    private int turn;
    private static final int maxTurn = 6;
    private List<PlayerInterface> players;
    private boolean gameWon;

    public NumberGuessGame() {
        Random random = new Random();
        this.number = random.nextInt(100) + 1;
        this.turn = 0;
        gameWon = false;
        players = new ArrayList<>();
    }

    public void run() {
        if(players.isEmpty()) {
                System.out.println("At least 1 player needed.");
                return;
            }
        printGameStart();
        while (turn < maxTurn && !gameWon) {
            for(PlayerInterface player : players) {
                System.out.println("Player: " + player.getArcadeAccount().getAccountName() + ", it's your turn.");
                int guess = player.play();
                turn++;
                if(guess == number){
                    System.out.println("Congratulation " + player.getArcadeAccount().getAccountName() + " wins!");
                    gameWon = true;
                    break;
                }
                if(guess < number) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Too High!");
                }
                if (turn >= maxTurn) {
                    break;
                }
            }
        }
        if(!gameWon) {
            System.out.println("Game Over.");
            System.out.println("The number was " + number);
        }
    }

    public void printGameStart() {
        System.out.println("Welcome to the Number Guess Game! A number between 1 and 100 is randomly chosen. Can you guess it?");
        System.out.println("You have " + maxTurn/players.size() + " guesses. Good luck!");
    }

    @Override
    public void add(PlayerInterface player) {
        if(players.size() < 2) {
            players.add(player);
        }
    }

    @Override
    public void remove(PlayerInterface player) {
        players.remove(player);
    }

}