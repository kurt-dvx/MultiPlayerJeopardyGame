package com.comp3607;

import com.comp3607.service.*;
import com.comp3607.factory.QuestionParserFactory;
import com.comp3607.observer.GameNotifier;
import com.comp3607.strategy.ReportGenerator;
import com.comp3607.strategy.TextReportGenerator;
import com.comp3607.strategy.ConsoleIOStrategy;
import com.comp3607.UI.JeopardyFrame;
import com.comp3607.UI.ConsoleUI;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Check if GUI mode is requested
        if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            launchGUI();
        } else {
            launchConsole();
        }
    }
    
    private static void launchGUI() {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel - CORRECTED
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Launch the GUI
            JeopardyFrame frame = new JeopardyFrame();
            frame.setVisible(true);
        });
    }
    
    private static void launchConsole() {
        Scanner scanner = new Scanner(System.in);
        EventLogService logService = new EventLogService();
        
        // USE STRATEGY PATTERN - but functionality identical!
        ConsoleIOStrategy ioStrategy = new ConsoleIOStrategy(scanner);
        ConsoleUI ui = new ConsoleUI(logService, ioStrategy, ioStrategy);
        
        try {
            ui.displayWelcome();
            logService.logSystemEvent("Start Game");
            
            // Setup
            List<Player> players = ui.setupPlayers();
            List<Question> questions = loadQuestions("sample_game_CSV.csv", logService);
            
            // Game engine
            GameNotifier notifier = new GameNotifier();
            notifier.addObserver(logService);
            GameService gameService = new GameService(notifier);
            gameService.startGame(players, questions);
            
            // Game loop
            runGame(gameService, ui, logService);
            
            // Reports
            generateReports(gameService, logService);
            logService.logSystemEvent("Exit Game");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static List<Question> loadQuestions(String fileName, EventLogService logService) {
        System.out.println("Loading questions from: " + fileName);
        logService.logFileLoad(fileName, true);
        
        var parser = QuestionParserFactory.createParser(fileName);
        var questions = parser.parse(fileName);
        
        if (questions.isEmpty()) {
            System.out.println("No questions loaded. Using sample questions.");
            questions = createSampleQuestions();
        }
        return questions;
    }
    
    private static List<Question> createSampleQuestions() {
        return Arrays.asList(
            new Question("Science", 100, "What is H2O?", "Water"),
            new Question("Science", 200, "What planet is red?", "Mars"),
            new Question("History", 100, "First US president?", "Washington"),
            new Question("History", 200, "WWII ended year?", "1945")
        );
    }
    
    private static void runGame(GameService gameService, ConsoleUI ui, EventLogService logService) {
        System.out.println("\n🎮 GAME STARTED!");
        
        while (!gameService.isGameOver()) {
            Player currentPlayer = gameService.getCurrentPlayer();
            ui.displayPlayerTurn(currentPlayer);
            
            String category = ui.selectCategory(gameService, currentPlayer);
            if (category == null) continue;
            if ("QUIT".equals(category)) {
                System.out.println("🛑 Game quit by user");
                logService.logSystemEvent("Game Quit Early");
                ui.showCurrentScores(gameService);
                return;
            }
            
            int value = ui.selectQuestionValue(gameService, category, currentPlayer);
            if (value == -1) continue;
            
            Question question = gameService.selectQuestion(category, value);
            if (question == null) continue;
            
            String answer = ui.askQuestion(question, category, value);
            
            // Check for quit command during answer
            if ("quit".equalsIgnoreCase(answer) || "exit".equalsIgnoreCase(answer)) {
                System.out.println("🛑 Game quit by user");
                logService.logSystemEvent("Game Quit Early");
                ui.showCurrentScores(gameService);
                return;
            }
            
            boolean isCorrect = gameService.submitAnswer(answer);
            
            logService.logScoreUpdate(currentPlayer.getId(), currentPlayer.getScore());
            ui.displayAnswerResult(isCorrect, value, question, currentPlayer);
            
            gameService.nextTurn();
        }
        
        ui.showFinalResults(gameService);
    }
    
    private static void generateReports(GameService gameService, EventLogService logService) {
        try {
            System.out.println("\n📄 Generating reports...");
            
            ReportGenerator reporter = new TextReportGenerator();
            reporter.generateReport(gameService.getSession(), "game_report.txt");
            logService.generateEventLog("game_event_log.csv");
            
            System.out.println("✅ Reports generated: game_report.txt, game_event_log.csv");
            
        } catch (Exception e) {
            System.err.println("❌ Error generating reports: " + e.getMessage());
        }
    }
}