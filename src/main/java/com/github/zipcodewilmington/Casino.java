package com.github.zipcodewilmington;

import java.util.ArrayList;
import java.util.List;

import com.github.zipcodewilmington.casino.CasinoAccount;
import com.github.zipcodewilmington.casino.CasinoAccountManager;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.PlayerInterface;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessGame;
import com.github.zipcodewilmington.casino.games.numberguess.NumberGuessPlayer;
import com.github.zipcodewilmington.casino.games.slots.SlotsGame;
import com.github.zipcodewilmington.casino.games.slots.SlotsPlayer;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

/**
 * Created by leon on 7/21/2020.
 */
public class Casino implements Runnable {
    private final IOConsole console = new IOConsole(AnsiColor.GREEN);

    @Override
    public void run() {
        IOConsole integer = new IOConsole();
        int numberOfPlayers;
        String arcadeDashBoardInput;
        CasinoAccountManager casinoAccountManager = new CasinoAccountManager();
        do {
            arcadeDashBoardInput = getArcadeDashboardInput();
            if ("select-game".equals(arcadeDashBoardInput)) {
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount casinoAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                boolean isValidLogin = casinoAccount != null;
                if (isValidLogin) {
                    String gameSelectionInput = getGameSelectionInput().toUpperCase();
                    if (gameSelectionInput.equals("SLOTS")) {
                        play(new SlotsGame(), new SlotsPlayer(casinoAccount));
                    } else if (gameSelectionInput.equals("NUMBERGUESS")) {
                        numberOfPlayers = integer.getIntegerInput("How many players? (1 or 2)");
                        List <CasinoAccount> players = new ArrayList<>();
                        players.add(casinoAccount);
                        for(int i = 1; i < numberOfPlayers; i++) {
                            accountName = console.getStringInput("Enter second player account name:");
                            accountPassword = console.getStringInput("Enter second player account password:");
                            CasinoAccount playerAccount = casinoAccountManager.getAccount(accountName, accountPassword);
                            if (playerAccount == null) {
                                System.out.println("Invalid login. Try again.");
                                i--;
                                continue;
                            }
                            if (players.contains(playerAccount)) {
                                System.out.println("Player is already logged in.");
                                i--;
                                continue;
                            }
                            players.add(playerAccount);
                        }
                        NumberGuessGame game = new NumberGuessGame();
                        for(CasinoAccount player: players) {
                            NumberGuessPlayer numberGuessPlayer = new NumberGuessPlayer(player);
                            game.add(numberGuessPlayer);
                        }
                        game.run();

                    } else if (gameSelectionInput.equals("BLACKJACK")) {
                        play(new SlotsGame(), new SlotsPlayer(casinoAccount));
                    } else {
                        // TODO - implement better exception handling
                        String errorMessage = "[ %s ] is an invalid game selection";
                        throw new RuntimeException(String.format(errorMessage, gameSelectionInput));
                    }
                } else {
                    // TODO - implement better exception handling
                    String errorMessage = "No account found with name of [ %s ] and password of [ %s ]";
                    throw new RuntimeException(String.format(errorMessage, accountPassword, accountName));
                }
            } else if ("create-account".equals(arcadeDashBoardInput)) {
                console.println("Welcome to the account-creation screen.");
                String accountName = console.getStringInput("Enter your account name:");
                String accountPassword = console.getStringInput("Enter your account password:");
                CasinoAccount newAccount = casinoAccountManager.createAccount(accountName, accountPassword);
                casinoAccountManager.registerAccount(newAccount);
            }
        } while (!"logout".equals(arcadeDashBoardInput));
    }

    private String getArcadeDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Go Big or Go Home Casino!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ create-account ], [ select-game ]")
                .toString());
    }

    private String getGameSelectionInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Game Selection Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ SLOTS ], [ NUMBERGUESS ], [BLACKJACK]")
                .toString());
    }

    private void play(Object gameObject, Object playerObject) {
        GameInterface game = (GameInterface)gameObject;
        PlayerInterface player = (PlayerInterface)playerObject;
        game.add(player);
        game.run();
    }
}
