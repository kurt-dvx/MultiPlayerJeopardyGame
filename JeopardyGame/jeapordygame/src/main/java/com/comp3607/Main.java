package com.comp3607;

import com.comp3607.service.*;
import com.comp3607.factory.QuestionParserFactory;
import com.comp3607.observer.GameNotifier;
import com.comp3607.strategy.TextReportGenerator;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎯 Jeopardy Game Starting...");
        
        Scanner scanner = new Scanner(System.in);
        EventLogService logService = new EventLogService();
        
        try {
            // Log Start Game
            logService.logSystemEvent("Start Game");
            
            // Setup players
            List<Player> players = setupPlayers(scanner, logService);
            
            // Load questions and log
            String fileName = "sample_game_CSV.csv";
            System.out.println("Loading questions from: " + fileName);
            logService.logFileLoad(fileName, true);
            
            QuestionParser parser = QuestionParserFactory.createParser(fileName);
            List<Question> questions = parser.parse(fileName);
            
            if (questions.isEmpty()) {
                System.out.println("No questions loaded from file. Using sample questions.");
                questions = createSampleQuestions();
            }
            
            // Log player count
            logService.logPlayerCount(players.size());
            
            // Setup game services
            GameNotifier notifier = new GameNotifier();
            notifier.addObserver(logService);
            
            GameService gameService = new GameService(notifier);
            
            // Start game
            gameService.startGame(players, questions);
            System.out.println("✓ Game started with " + players.size() + " players and " + questions.size() + " questions");
            
            // Game loop
            playGame(gameService, scanner, logService);
            
            // Generate reports and log
            logService.logSystemEvent("Generate Report");
            logService.logSystemEvent("Generate Event Log");
            generateReports(gameService, logService);
            
            // Log exit
            logService.logSystemEvent("Exit Game");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static List<Player> setupPlayers(Scanner scanner, EventLogService logService) {
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
            
            // Log player name entry
            logService.logPlayerName(playerId, name);
        }
        
        System.out.println("✓ Players setup: " + players.size() + " players ready");
        return players;
    }
    
    private static List<Question> createSampleQuestions() {
        List<Question> questions = new ArrayList<>();
        // Add some sample questions if CSV fails to load
        questions.add(new Question("Science", 100, "What is the chemical symbol for water?", "H2O"));
        questions.add(new Question("Science", 200, "What planet is known as the Red Planet?", "Mars"));
        questions.add(new Question("History", 100, "Who was the first president of the United States?", "George Washington"));
        questions.add(new Question("History", 200, "In what year did World War II end?", "1945"));
        questions.add(new Question("Geography", 100, "What is the capital of France?", "Paris"));
        questions.add(new Question("Geography", 200, "Which river is the longest in the world?", "Nile"));
        return questions;
    }
    
    private static void playGame(GameService gameService, Scanner scanner, EventLogService logService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎮 GAME STARTED!");
        System.out.println("=".repeat(50));
        
        while (!gameService.isGameOver()) {
            Player currentPlayer = gameService.getCurrentPlayer();
            System.out.println("\n" + "─".repeat(40));
            System.out.println("🎯 " + currentPlayer.getName() + "'s Turn");
            System.out.println("💰 Current Score: $" + currentPlayer.getScore());
            System.out.println("─".repeat(40));
            
            // Show available categories with numbers
            List<String> categories = gameService.getCategories();
            if (categories.isEmpty()) {
                System.out.println("❌ No more questions available!");
                break;
            }
            
            System.out.println("📚 Available Categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + categories.get(i));
            }
            
            System.out.print("👉 Choose a category (1-" + categories.size() + "): ");
            int categoryChoice;
            try {
                categoryChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number.");
                continue;
            }
            
            // Validate category choice
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("❌ Invalid choice. Please choose between 1 and " + categories.size());
                continue;
            }
            
            String selectedCategory = categories.get(categoryChoice - 1);
            
            // Log category selection
            logService.logCategorySelection(currentPlayer.getId(), selectedCategory);
            
            // Show available values for chosen category with numbers
            List<Integer> values = gameService.getAvailableValues(selectedCategory);
            if (values.isEmpty()) {
                System.out.println("❌ No questions left in that category. Choose another.");
                continue;
            }
            
            System.out.println("💰 Available Values for " + selectedCategory + ":");
            for (int i = 0; i < values.size(); i++) {
                System.out.println("   " + (i + 1) + ". $" + values.get(i));
            }
            
            System.out.print("👉 Choose a value (1-" + values.size() + "): ");
            int valueChoice;
            try {
                valueChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice. Please enter a number.");
                continue;
            }
            
            // Validate value choice
            if (valueChoice < 1 || valueChoice > values.size()) {
                System.out.println("❌ Invalid choice. Please choose between 1 and " + values.size());
                continue;
            }
            
            int selectedValue = values.get(valueChoice - 1);
            
            // Log question selection
            logService.logQuestionSelection(currentPlayer.getId(), selectedCategory, selectedValue);
            
            // Select and display question
            Question question = gameService.selectQuestion(selectedCategory, selectedValue);
            if (question == null) {
                System.out.println("❌ Question not available. Choose another.");
                continue;
            }
            
            System.out.println("\n" + "?".repeat(50));
            System.out.println("❓ QUESTION (" + selectedCategory + " - $" + selectedValue + "):");
            System.out.println("?".repeat(50));
            System.out.println(question.getQuestionText());
            System.out.println("?".repeat(50));
            System.out.print("💡 Your answer: ");
            String answer = scanner.nextLine();
            
            // Submit answer
            boolean isCorrect = gameService.submitAnswer(answer);
            
            // Log score update
            logService.logScoreUpdate(currentPlayer.getId(), currentPlayer.getScore());
            
            if (isCorrect) {
                System.out.println("✅ CORRECT! +$" + selectedValue);
            } else {
                int pointsLost = Math.min(selectedValue, currentPlayer.getScore() + selectedValue);
                System.out.println("❌ WRONG! -$" + pointsLost);
                System.out.println("💡 Correct answer was: " + question.getAnswer());
            }
            
            System.out.println("💰 New Score: $" + currentPlayer.getScore());
            
            // Move to next player
            gameService.nextTurn();
            
            // Brief pause for readability
            System.out.println("\n⏳ Continuing to next player...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Continue without delay
            }
        }
        
        // Game over - show final results
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
    
    private static void generateReports(GameService gameService, EventLogService logService) {
        try {
            System.out.println("\n📄 Generating reports...");
            
            // Generate text report
            TextReportGenerator reportGenerator = new TextReportGenerator();
            reportGenerator.generateReport(gameService.getSession(), "game_report.txt");
            
            // Generate event log
            logService.generateEventLog("game_event_log.csv");
            
            System.out.println("✅ Reports generated:");
            System.out.println("   📝 game_report.txt - Game summary report");
            System.out.println("   📊 game_event_log.csv - Process mining event log");
            
        } catch (Exception e) {
            System.err.println("❌ Error generating reports: " + e.getMessage());
        }
    }
}