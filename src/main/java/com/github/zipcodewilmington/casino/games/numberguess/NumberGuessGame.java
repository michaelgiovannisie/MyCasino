package com.github.zipcodewilmington.casino.games.numberguess;

import java.util.Random;
import java.util.Scanner;

import com.github.zipcodewilmington.casino.GameInterface;

public class NumberGuessGame implements GameInterface {

    private int number;
    private int guesses;
    private static final int MAX_GUESSES = 7;

    public NumberGuessGame() {
        Random random = new Random();
        this.number = random.nextInt(100) + 1;
        this.guesses = 0;
    }

    public void play(Scanner scanner) {
        printGameStart();
        while (guesses < MAX_GUESSES) {

                System.out.println("Enter your guess: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                    continue;
                }

                int guess = scanner.nextInt();
                if (guess < 1 || guess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }

                this.guesses++;

                if (guess == this.number) {
                    System.out.println("Congratulations! You guessed the number " + this.number + " in " + this.guesses + " guesses!");
                    break;
                }

                else if (guess < this.number) {
                    System.out.println("Too low! You have " + (MAX_GUESSES - this.guesses) + " guesses left.");
                }

                else if (guess > this.number) {
                    System.out.println("Too high! You have " + (MAX_GUESSES - this.guesses) + " guesses left.");
                }

                if (guesses >= MAX_GUESSES) {
                    System.out.printf("Sorry, you've run out of guesses! The number was " + this.number + ".");
                    System.out.println("The secret number was: " + this.number);
                    break;
                }
        }
    }

    public void printGameStart() {
        System.out.println("Welcome to the Number Guess Game! A number between 1 and 100 is randomly chosen. Can you guess it?");
        System.out.println("You have " + MAX_GUESSES + " guesses. Good luck!");
    }

}