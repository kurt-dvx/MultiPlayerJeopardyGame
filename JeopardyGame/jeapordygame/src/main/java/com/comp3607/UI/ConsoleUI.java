package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.strategy.InputStrategy;
import com.comp3607.strategy.OutputStrategy;
import com.comp3607.strategy.ConsoleIOStrategy;
import com.comp3607.model.Player;
import com.comp3607.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUI implements GameUI {
    private EventLogService logService;
    private InputStrategy input;
    private OutputStrategy output;
    
    // Constructor that uses strategies instead of Scanner
    public ConsoleUI(EventLogService logService, InputStrategy input, OutputStrategy output) {
        this.logService = logService;
        this.input = input;
        this.output = output;
    }
    
    // Constructor for backward compatibility
    public ConsoleUI(EventLogService logService, java.util.Scanner scanner) {
        this(logService, new ConsoleIOStrategy(scanner), new ConsoleIOStrategy(scanner));
    }
    
    @Override
    public void displayWelcome() {
        output.displayBanner("Jeopardy Game Starting...");
    }
    
    @Override
    public List<Player> setupPlayers() {
        List<Player> players = new ArrayList<>();
        
        int playerCount = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                playerCount = input.readInt("Enter number of players (1-4): ");
                
                if (playerCount >= 1 && playerCount <= 4) {
                    validInput = true;
                } else {
                    output.displayError("Please enter a number between 1 and 4");
                }
            } catch (IllegalArgumentException e) {
                output.displayError(e.getMessage());
            }
        }
        
        for (int i = 0; i < playerCount; i++) {
            String name = input.readLine("Enter name for Player " + (i + 1) + ": ");
            if (name.trim().isEmpty()) {
                name = "Player " + (i + 1);
            }
            String playerId = "P" + (i + 1);
            players.add(new Player(playerId, name));
            
            logService.logPlayerName(playerId, name);
        }
        
        logService.logPlayerCount(players.size());
        output.displayFormatted("Players setup: " + players.size() + " players ready");
        return players;
    }
    
    @Override
    public void displayPlayerTurn(Player player) {
        output.displayBanner(player.getName() + "'s Turn");
        output.display("Current Score: $" + player.getScore());
    }
    
    @Override
    public String selectCategory(GameService gameService, Player player) {
        List<String> categories = gameService.getCategories();
        output.display("Available Categories:");
        for (int i = 0; i < categories.size(); i++) {
            output.display("   " + (i + 1) + ". " + categories.get(i));
        }
        
        try {
            String userInput = input.readLine("Choose a category (1-" + categories.size() + ") or type 'QUIT' to end game: ");
            
            // Quit Check
            if ("QUIT".equalsIgnoreCase(userInput)) {
                return "QUIT";
            }
            
            int choice = Integer.parseInt(userInput);
            if (choice < 1 || choice > categories.size()) {
                output.displayError("Invalid choice. Please select a valid category.");
                return null;
            }
            
            String selectedCategory = categories.get(choice - 1);
            logService.logCategorySelection(player.getId(), selectedCategory);
            return selectedCategory;
            
        } catch (NumberFormatException e) {
            output.displayError("Invalid choice. Please enter a number or 'QUIT'.");
            return null;
        }
    }
    
    @Override
    public int selectQuestionValue(GameService gameService, String category, Player player) {
        List<Integer> values = gameService.getAvailableValues(category);
        output.display("Available Values for " + category + ":");
        for (int i = 0; i < values.size(); i++) {
            output.display("   " + (i + 1) + ". $" + values.get(i));
        }
        
        try {
            int choice = input.readInt("Choose a value (1-" + values.size() + "): ");
            if (choice < 1 || choice > values.size()) {
                output.displayError("Invalid choice. Please select a valid value.");
                return -1;
            }
            
            int selectedValue = values.get(choice - 1);
            logService.logQuestionSelection(player.getId(), category, selectedValue);
            return selectedValue;
            
        } catch (IllegalArgumentException e) {
            output.displayError("Invalid choice. Please enter a number.");
            return -1;
        }
    }
    
    @Override
    public String askQuestion(Question question, String category, int value) {
        output.displayBanner("QUESTION (" + category + " - $" + value + ")");
        output.display(question.getQuestionText());
        
        // REMOVED quit instruction - no quitting during answers
        String answer = input.readLine("=> Your answer: ");
        return answer;
    }
    
    @Override
    public void displayAnswerResult(boolean isCorrect, int value, Question question, Player player) {
        if (isCorrect) {
            output.displayFormatted("☑️ CORRECT! +$" + value);
        } else {
            int pointsLost = Math.min(value, player.getScore() + value);
            output.displayError("WRONG! -$" + pointsLost);
            output.display("Correct answer was: " + question.getAnswer());
        }
        output.display("New Score: $" + player.getScore());
    }
    
    @Override
    public void showFinalResults(GameService gameService) {
        output.displayBanner("GAME OVER!");
        output.display("=> FINAL SCORES:");
        
        List<Player> players = gameService.getPlayers();
        Player winner = players.get(0);
        
        for (Player player : players) {
            output.display("   " + player.getName() + ": $" + player.getScore());
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        
        output.displayFormatted("🏆 WINNER: " + winner.getName() + " with $" + winner.getScore() + "!");
    }

    @Override
    public void showCurrentScores(GameService gameService) {
        output.displayBanner("GAME STOPPED - CURRENT SCORES");
        output.display("CURRENT SCORES:");
        
        List<Player> players = gameService.getPlayers();
        Player leader = players.get(0);
        
        for (Player player : players) {
            output.display("   " + player.getName() + ": $" + player.getScore());
            if (player.getScore() > leader.getScore()) {
                leader = player;
            }
        }
        
        output.displayFormatted("🏆 CURRENT LEADER: " + leader.getName() + " with $" + leader.getScore() + "!");
        output.display("🙏 Thanks for playing!");
    }
}