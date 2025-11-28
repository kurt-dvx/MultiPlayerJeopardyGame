package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.Scanner;
import java.util.List;

public class GameplayUI {
    private EventLogService logService;
    private Scanner scanner;
    
    public GameplayUI(EventLogService logService, Scanner scanner) {
        this.logService = logService;
        this.scanner = scanner;
    }
    
    public void displayPlayerTurn(Player player) {
        System.out.println("\n" + "=".repeat(30));
        System.out.println(player.getName() + "'s Turn");
        System.out.println("Current Score: $" + player.getScore());
        System.out.println("=".repeat(30));
    }
    
    public String selectCategory(GameService gameService, Player player) {
        List<String> categories = gameService.getCategories();
        displayOptions("Available Categories:", categories);
        
        try {
            System.out.print("Choose a category (1-" + categories.size() + ") or type 'QUIT' to end game: ");
            String input = scanner.nextLine();
            
            if ("QUIT".equalsIgnoreCase(input)) return "QUIT";
            
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > categories.size()) {
                System.out.println("Invalid choice. Please select a valid category.");
                return null;
            }
            
            String category = categories.get(choice - 1);
            logService.logCategorySelection(player.getId(), category);
            return category;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number or 'QUIT'.");
            return null;
        }
    }
    
    public int selectQuestionValue(GameService gameService, String category, Player player) {
        List<Integer> values = gameService.getAvailableValues(category);
        System.out.println("Available Values for " + category + ":");
        for (int i = 0; i < values.size(); i++) {
            System.out.println("   " + (i + 1) + ". $" + values.get(i));
        }
        
        try {
            System.out.print("Choose a value (1-" + values.size() + "): ");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > values.size()) {
                System.out.println("Invalid choice. Please select a valid value.");
                return -1;
            }
            
            int value = values.get(choice - 1);
            logService.logQuestionSelection(player.getId(), category, value);
            return value;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number.");
            return -1;
        }
    }
    
    public String askQuestion(Question question, String category, int value) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("QUESTION (" + category + " - $" + value + ")");
        System.out.println("=".repeat(50));
        System.out.println(question.getQuestionText());
        System.out.print("Your answer: ");
        return scanner.nextLine();
    }
    
    public void displayAnswerResult(boolean isCorrect, int value, Question question, Player player) {
        if (isCorrect) {
            System.out.println("CORRECT! +$" + value);
        } else {
            System.out.println("WRONG! -$" + value);
            System.out.println("Correct answer was: " + question.getAnswer());
        }
        System.out.println("New Score: $" + player.getScore());
    }
    
    private void displayOptions(String title, List<String> options) {
        System.out.println(title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + options.get(i));
        }
    }
}