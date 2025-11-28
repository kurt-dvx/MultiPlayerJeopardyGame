package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.model.Player;
import java.util.List;

public class ResultsUI {
    public void displayWelcome() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("WELCOME AND ENJOY OUR SIMPLE JEOPARDY GAME");
        System.out.println("=".repeat(50));
    }
    
    public void showFinalResults(GameService gameService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("GAME OVER!");
        System.out.println("=".repeat(50));
        showScores("FINAL SCORES:", gameService);
    }

    public void showCurrentScores(GameService gameService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("GAME STOPPED");
        System.out.println("=".repeat(50));
        showScores("CURRENT SCORES:", gameService);
        System.out.println("Thanks for playing!");
    }
    
    private void showScores(String title, GameService gameService) {
        System.out.println(title);
        List<Player> players = gameService.getPlayers();
        Player winner = players.get(0);
        
        for (Player player : players) {
            System.out.println("   " + player.getName() + ": $" + player.getScore());
            if (player.getScore() > winner.getScore()) winner = player;
        }
        
        System.out.println("\nWINNER: " + winner.getName() + " with $" + winner.getScore() + "!");
    }
}