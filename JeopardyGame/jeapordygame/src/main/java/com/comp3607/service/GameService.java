package com.comp3607.service;

import com.comp3607.model.*;
import com.comp3607.observer.GameNotifier;
import com.comp3607.builder.GameEventBuilder;
import java.util.*;

public class GameService {
    private GameSession session;
    private GameNotifier notifier;
    private String caseId;
    private Question currentQuestion; // ← MISSING FIELD
    
    public GameService(GameNotifier notifier) {
        this.notifier = notifier;
        this.caseId = UUID.randomUUID().toString();
    }
    
    public void startGame(List<Player> players, List<Question> questions) {
        this.session = new GameSession(players, questions);
        
        GameEvent event = new GameEventBuilder(caseId, "Start Game") // ← FIXED CONSTRUCTOR
            .build();
        notifier.notifyObservers(event);
    }
    
    // ← MISSING METHOD: Get available categories
    public List<String> getCategories() {
        Set<String> categories = new HashSet<>();
        for (Question q : session.getQuestions()) {
            if (!q.isUsed()) {
                categories.add(q.getCategory());
            }
        }
        return new ArrayList<>(categories);
    }
    
    // ← MISSING METHOD: Get available values for category
    public List<Integer> getAvailableValues(String category) {
        Set<Integer> values = new HashSet<>();
        for (Question q : session.getQuestions()) {
            if (!q.isUsed() && q.getCategory().equals(category)) {
                values.add(q.getValue());
            }
        }
        return new ArrayList<>(values);
    }
    
    // ← MISSING METHOD: Select question implementation
    public Question selectQuestion(String category, int value) {
        for (Question q : session.getQuestions()) {
            if (!q.isUsed() && q.getCategory().equals(category) && q.getValue() == value) {
                q.setUsed(true);
                currentQuestion = q;
                
                Player currentPlayer = session.getCurrentPlayer();
                GameEvent event = new GameEventBuilder(caseId, "Select Question")
                    .withPlayerId(currentPlayer.getId())
                    .withCategory(category)
                    .withQuestionValue(value)
                    .build();
                notifier.notifyObservers(event);
                
                return q;
            }
        }
        return null;
    }
    
    // ← MISSING METHOD: Answer evaluation
    private boolean evaluateAnswer(String answer) {
        return currentQuestion != null && currentQuestion.checkMultipleChoiceAnswer(answer);
    }
    
    public boolean submitAnswer(String answer) { // ← REMOVED Player parameter
        if (currentQuestion == null) return false;
        
        Player currentPlayer = session.getCurrentPlayer();
        boolean isCorrect = evaluateAnswer(answer);
        
        if (isCorrect) {
            currentPlayer.addScore(currentQuestion.getValue());
        } else {
            currentPlayer.addScore(-currentQuestion.getValue());
        }
        
        String turnInfo = String.format("%s: %s - $%d - Answer: '%s' - %s - Score: $%d",
            currentPlayer.getName(),
            currentQuestion.getCategory(),
            currentQuestion.getValue(),
            answer,
            isCorrect ? "Correct" : "Wrong",
            currentPlayer.getScore()
        );
        session.addTurnHistory(turnInfo);
        
        GameEvent event = new GameEventBuilder(caseId, "Answer Question")
            .withPlayerId(currentPlayer.getId())
            .withCategory(currentQuestion.getCategory())
            .withQuestionValue(currentQuestion.getValue())
            .withAnswerGiven(answer)
            .withResult(isCorrect ? "Correct" : "Incorrect")
            .withScoreAfterPlay(currentPlayer.getScore())
            .build();
        notifier.notifyObservers(event);
        
        return isCorrect;
    }
    
    // ← MISSING METHOD: Next turn
    public void nextTurn() {
        session.nextTurn();
    }
    
    // ← MISSING METHOD: Get current player
    public Player getCurrentPlayer() {
        return session.getCurrentPlayer();
    }
    
    // ← MISSING METHOD: Check if game over
    public boolean isGameOver() {
        for (Question q : session.getQuestions()) {
            if (!q.isUsed()) {
                return false;
            }
        }
        return true;
    }
    
    // ← MISSING METHOD: Get players
    public List<Player> getPlayers() {
        return session.getPlayers();
    }
    
    // ← MISSING METHOD: Get session
    public GameSession getSession() {
        return session;
    }
}