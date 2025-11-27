package com.comp3607.composite;

import com.comp3607.service.GameService;
import com.comp3607.UI.GameUI;
import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;

public class GameplayPhase implements GamePhase {
    private GameService gameService;
    private GameUI ui;
    private EventLogService logService;
    private boolean successful;
    private boolean gameCompleted;
    
    public GameplayPhase(GameService gameService, GameUI ui, EventLogService logService) {
        this.gameService = gameService;
        this.ui = ui;
        this.logService = logService;
        this.successful = false;
        this.gameCompleted = false;
    }
    
    @Override
    public void execute() {
        try {
            if (gameService == null) {
                System.err.println("Cannot start gameplay - GameService is null");
                successful = false;
                return;
            }
            
            System.out.println("\nGAME STARTED!");
            System.out.println("To End Game Type 'QUIT' during category selection");
            
            boolean userQuit = false;
            
            while (!gameService.isGameOver() && !userQuit) {
                Player currentPlayer = gameService.getCurrentPlayer();
                ui.displayPlayerTurn(currentPlayer);
                
                String category = ui.selectCategory(gameService, currentPlayer);
                if (category == null) continue;
                
                // ONLY QUIT CHECK - at category selection
                if ("QUIT".equals(category)) {
                    System.out.println("Game quit by " + currentPlayer.getName());
                    logService.logSystemEvent("Game Quit by " + currentPlayer.getName());
                    ui.showCurrentScores(gameService);
                    userQuit = true;
                    break;
                }
                
                int value = ui.selectQuestionValue(gameService, category, currentPlayer);
                if (value == -1) continue;
                
                Question question = gameService.selectQuestion(category, value);
                if (question == null) continue;
                
                String answer = ui.askQuestion(question, category, value);
                
                boolean isCorrect = gameService.submitAnswer(answer);
                logService.logScoreUpdate(currentPlayer.getId(), currentPlayer.getScore());
                ui.displayAnswerResult(isCorrect, value, question, currentPlayer);
                
                gameService.nextTurn();
            }
            
            if (!userQuit && gameService.isGameOver()) {
                ui.showFinalResults(gameService);
                gameCompleted = true;
            }
            
            successful = true;
            System.out.println("Gameplay completed");
            
        } catch (Exception e) {
            System.err.println("Gameplay failed: " + e.getMessage());
            e.printStackTrace();
            successful = false;
        }
    }
    
    @Override
    public String getPhaseName() {
        return "Gameplay";
    }
    
    @Override
    public boolean isSuccessful() {
        return successful;
    }
    
    public boolean isGameCompleted() {
        return gameCompleted;
    }
}