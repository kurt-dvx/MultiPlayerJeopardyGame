package com.comp3607.service;

import com.comp3607.model.*;
import com.comp3607.observer.GameNotifier;
import java.util.*;

public class GameService {
    private GameSession session;
    private GameNotifier notifier;
    private EventLogService logService;
    private Question currentQuestion;
    
    public GameService(GameNotifier notifier, EventLogService logService) {
        this.notifier = notifier;
        this.logService = logService;
    }
    
    public void startGame(List<Player> players, List<Question> questions) {
        this.session = new GameSession(players, questions);
        logService.logSystemEvent("Start Game");
        
        // turn history for report
        for (Player player : players) {
            session.addTurnHistory("Player joined: " + player.getName());
        }
    }
    
    public List<String> getCategories() {
        Set<String> categories = new HashSet<>();
        for (Question q : session.getQuestions()) {
            if (!q.isUsed()) {
                categories.add(q.getCategory());
            }
        }
        return new ArrayList<>(categories);
    }
    
    public List<Integer> getAvailableValues(String category) {
        Set<Integer> values = new HashSet<>();
        for (Question q : session.getQuestions()) {
            if (!q.isUsed() && q.getCategory().equals(category)) {
                values.add(q.getValue());
            }
        }
        return new ArrayList<>(values);
    }
    
    public Question selectQuestion(String category, int value) {
        for (Question q : session.getQuestions()) {
            if (!q.isUsed() && q.getCategory().equals(category) && q.getValue() == value) {
                q.setUsed(true);
                currentQuestion = q;
                
                Player currentPlayer = session.getCurrentPlayer();
                logService.logQuestionSelection(currentPlayer.getId(), category, value);
                
                // turn history for report
                String turnInfo = currentPlayer.getName() + " selected " + category + " for " + value + " pts";
                session.addTurnHistory(turnInfo);
                
                return q;
            }
        }
        return null;
    }
    
    private boolean evaluateAnswer(String answer) {
        return currentQuestion != null && currentQuestion.checkAnswer(answer);
    }
    
    public boolean submitAnswer(String answer) {
        if (currentQuestion == null) return false;
        
        Player currentPlayer = session.getCurrentPlayer();
        boolean isCorrect = evaluateAnswer(answer);
        
        if (isCorrect) {
            currentPlayer.addScore(currentQuestion.getValue());
        } else {
            currentPlayer.subtractScore(currentQuestion.getValue());
        }
        
        logService.logAnswer(currentPlayer.getId(), currentQuestion.getCategory(),
                           currentQuestion.getValue(), answer, isCorrect, currentPlayer.getScore());
        
        // turn history for report
        String turnInfo = String.format("Question: %s\nAnswer: %s — %s (+%d pts)\nScore after turn: %s = %d",
            currentQuestion.getQuestionText().split("\n")[0], // First line only
            answer,
            isCorrect ? "Correct" : "Wrong",
            isCorrect ? currentQuestion.getValue() : -currentQuestion.getValue(),
            currentPlayer.getName(),
            currentPlayer.getScore()
        );
        session.addTurnHistory(turnInfo);
        
        return isCorrect;
    }

    public void nextTurn() {
        session.nextTurn();
    }
    
    public Player getCurrentPlayer() {
        return session.getCurrentPlayer();
    }
    
    public boolean isGameOver() {
        for (Question q : session.getQuestions()) {
            if (!q.isUsed()) {
                return false;
            }
        }
        return true;
    }
    
    public List<Player> getPlayers() {
        return session.getPlayers();
    }
    
    public GameSession getSession() {
        return session;
    }
}