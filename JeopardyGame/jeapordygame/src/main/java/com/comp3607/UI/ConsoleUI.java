package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private EventLogService logService;
    private Scanner scanner;
    
    public ConsoleUI(EventLogService logService, Scanner scanner) {
        this.logService = logService;
        this.scanner = scanner;
    }
    
    public void displayWelcome() {
        System.out.println("Jeopardy Game Starting...");
    }
    
    public List<Player> setupPlayers() {
        List<Player> players = new ArrayList<>();
        
        System.out.print("Enter number of players (1-4): ");
        int playerCount = Integer.parseInt(scanner.nextLine());
        
        for (int i = 0; i < playerCount; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                name = "Player " + (i + 1);
            }
            String playerId = "P" + (i + 1);
            players.add(new Player(playerId, name));
            
            logService.logPlayerName(playerId, name);
        }
        
        logService.logPlayerCount(players.size());
        System.out.println("✓ Players setup: " + players.size() + " players ready");
        return players;
    }
    
    public void displayPlayerTurn(Player player) {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("🎯 " + player.getName() + "'s Turn");
        System.out.println("💰 Current Score: $" + player.getScore());
        System.out.println("─".repeat(40));
    }
    
    public String selectCategory(GameService gameService, Player player) {
        List<String> categories = gameService.getCategories();
        System.out.println("📚 Available Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + categories.get(i));
        }
        
        System.out.print("👉 Choose a category (1-" + categories.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > categories.size()) {
                System.out.println("❌ Invalid choice.");
                return null;
            }
            
            String selectedCategory = categories.get(choice - 1);
            logService.logCategorySelection(player.getId(), selectedCategory);
            return selectedCategory;
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid choice. Please enter a number.");
            return null;
        }
    }
    
    public int selectQuestionValue(GameService gameService, String category, Player player) {
        List<Integer> values = gameService.getAvailableValues(category);
        System.out.println("💰 Available Values for " + category + ":");
        for (int i = 0; i < values.size(); i++) {
            System.out.println("   " + (i + 1) + ". $" + values.get(i));
        }
        
        System.out.print("👉 Choose a value (1-" + values.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > values.size()) {
                System.out.println("❌ Invalid choice.");
                return -1;
            }
            
            int selectedValue = values.get(choice - 1);
            logService.logQuestionSelection(player.getId(), category, selectedValue);
            return selectedValue;
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid choice. Please enter a number.");
            return -1;
        }
    }
    
    public String askQuestion(Question question, String category, int value) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("❓ QUESTION (" + category + " - $" + value + "):");
        System.out.println("=".repeat(50));
        System.out.println(question.getQuestionText());
        System.out.println("=".repeat(50));
        System.out.print("💡 Your answer: ");
        return scanner.nextLine();
    }
    
    public void displayAnswerResult(boolean isCorrect, int value, Question question, Player player) {
        if (isCorrect) {
            System.out.println("✅ CORRECT! +$" + value);
        } else {
            int pointsLost = Math.min(value, player.getScore() + value);
            System.out.println("❌ WRONG! -$" + pointsLost);
            System.out.println("💡 Correct answer was: " + question.getAnswer());
        }
        System.out.println("💰 New Score: $" + player.getScore());
    }
    
    public void showFinalResults(GameService gameService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏁 GAME OVER!");
        System.out.println("=".repeat(50));
        System.out.println("📊 FINAL SCORES:");
        
        List<Player> players = gameService.getPlayers();
        Player winner = players.get(0);
        
        for (Player player : players) {
            System.out.println("   " + player.getName() + ": $" + player.getScore());
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        
        System.out.println("\n🏆 WINNER: " + winner.getName() + " with $" + winner.getScore() + "!");
        System.out.println("=".repeat(50));
    }

    public void showCurrentScores(GameService gameService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🛑 GAME STOPPED EARLY");
        System.out.println("=".repeat(50));
        System.out.println("📊 CURRENT SCORES:");
        
        List<Player> players = gameService.getPlayers();
        Player leader = players.get(0);
        
        for (Player player : players) {
            System.out.println("   " + player.getName() + ": $" + player.getScore());
            if (player.getScore() > leader.getScore()) {
                leader = player;
            }
        }
        
        System.out.println("\n🏅 CURRENT LEADER: " + leader.getName() + " with $" + leader.getScore() + "!");
        System.out.println("=".repeat(50));
    }
}