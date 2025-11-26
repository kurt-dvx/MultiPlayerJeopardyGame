package com.comp3607.controller;

import com.comp3607.service.*;
import com.comp3607.model.*;
import com.comp3607.observer.GameNotifier;
import com.comp3607.strategy.TextReportGenerator;
import java.util.List;

public class GameController {
    private GameService gameService;
    private EventLogService logService;
    
    public GameController() {
        this.logService = new EventLogService();
    }
    
    public void initializeGame(List<Player> players, List<Question> questions) {
        GameNotifier notifier = new GameNotifier();
        notifier.addObserver(logService);
        gameService = new GameService(notifier);
        gameService.startGame(players, questions);
        logService.logSystemEvent("Start Game");
    }
    
    public Question selectQuestion(String category, int value) {
        return gameService.selectQuestion(category, value);
    }
    
    public boolean submitAnswer(String answer) {
        boolean isCorrect = gameService.submitAnswer(answer);
        logService.logScoreUpdate(gameService.getCurrentPlayer().getId(), 
                               gameService.getCurrentPlayer().getScore());
        return isCorrect;
    }
    
    public void nextTurn() {
        gameService.nextTurn();
    }
    
    public boolean isGameOver() {
        return gameService.isGameOver();
    }
    
    public Player getCurrentPlayer() {
        return gameService.getCurrentPlayer();
    }
    
    public List<Player> getPlayers() {
        return gameService.getPlayers();
    }
    
    public List<String> getCategories() {
        return gameService.getCategories();
    }
    
    public List<Integer> getAvailableValues(String category) {
        return gameService.getAvailableValues(category);
    }
    
    public void generateReports() {
        TextReportGenerator reporter = new TextReportGenerator();
        reporter.generateReport(gameService.getSession(), "game_report.txt");
        logService.generateEventLog("game_event_log.csv");
    }
    
    public void quitGame() {
        logService.logSystemEvent("Game Quit Early");
        generateReports();
    }
}