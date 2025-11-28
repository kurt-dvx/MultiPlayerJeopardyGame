package com.comp3607;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.service.QuestionParser;
import com.comp3607.factory.QuestionParserFactory;
import com.comp3607.strategy.TextReportGenerator;
import com.comp3607.UI.ConsoleUI;
import com.comp3607.observer.GameNotifier;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventLogService logService = new EventLogService();
        ConsoleUI ui = new ConsoleUI(logService, scanner);
        
        try {
            // Phase 1: Setup
            ui.displayWelcome();
            List<Player> players = ui.setupPlayers();
            
            // Phase 2: Load Questions
            QuestionParser parser = QuestionParserFactory.createParser("sample_game_CSV.csv");
            List<Question> questions = parser.parse("sample_game_CSV.csv");
            logService.logFileLoad("sample_game_CSV.csv", !questions.isEmpty());
            
            // Phase 3: Initialize Game
            GameNotifier notifier = new GameNotifier();
            notifier.addObserver(logService);
            GameService gameService = new GameService(notifier, logService);
            gameService.startGame(players, questions);
            
            // Phase 4: Gameplay
            runGameplay(gameService, ui, logService);
            
            // Phase 5: Reporting (ALWAYS generate reports)
            generateReports(gameService, logService);
            
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static void runGameplay(GameService gameService, ConsoleUI ui, EventLogService logService) {
        System.out.println("\nGAME STARTED! Type 'QUIT' during category selection to end early.");
        
        while (!gameService.isGameOver()) {
            Player currentPlayer = gameService.getCurrentPlayer();
            ui.displayPlayerTurn(currentPlayer);
            
            String category = ui.selectCategory(gameService, currentPlayer);
            if ("QUIT".equals(category)) {
                logService.logSystemEvent("Game Quit by " + currentPlayer.getName());
                ui.showCurrentScores(gameService);
                return;
            }
            if (category == null) continue;
            
            int value = ui.selectQuestionValue(gameService, category, currentPlayer);
            if (value == -1) continue;
            
            Question question = gameService.selectQuestion(category, value);
            if (question == null) continue;
            
            String answer = ui.askQuestion(question, category, value);
            boolean isCorrect = gameService.submitAnswer(answer);
            
            ui.displayAnswerResult(isCorrect, value, question, currentPlayer);
            gameService.nextTurn();
        }
        
        ui.showFinalResults(gameService);
    }
    
    private static void generateReports(GameService gameService, EventLogService logService) {
        try {
            // Use the strategy pattern
            TextReportGenerator reporter = new TextReportGenerator();
            reporter.generateReport(gameService.getSession(), "game_report.txt");
            
            // Generate event log separately
            logService.generateEventLog("event_log.csv");
            
            System.out.println("Reports generated!");
        } catch (Exception e) {
            System.err.println("Error generating reports: " + e.getMessage());
        }
    }
}