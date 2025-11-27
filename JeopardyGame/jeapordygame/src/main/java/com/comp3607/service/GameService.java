package com.comp3607.service;

import com.comp3607.model.*;
import com.comp3607.observer.GameNotifier;
import com.comp3607.factory.GameEventFactory;
import java.util.*;

public class GameService {
    private GameSession session;
    private GameNotifier notifier;
    private String caseId;
    private Question currentQuestion;
    
    public GameService(GameNotifier notifier) {
        this.notifier = notifier;
        this.caseId = UUID.randomUUID().toString();
    }
    
    public void startGame(List<Player> players, List<Question> questions) {
        this.session = new GameSession(players, questions);
        
        // Using factory method
        GameEvent event = GameEventFactory.createSystemEvent(caseId, "Start Game");
        notifier.notifyObservers(event);
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
                GameEvent event = GameEventFactory.createQuestionSelectionEvent(
                    caseId, currentPlayer.getId(), category, value);
                notifier.notifyObservers(event);
                
                return q;
            }
        }
        return null;
    }
    
    private boolean evaluateAnswer(String answer) {
        return currentQuestion != null && currentQuestion.checkMultipleChoiceAnswer(answer);
    }
    
    public boolean submitAnswer(String answer) {
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
        
        // Using factory design pattern
        GameEvent event = GameEventFactory.createQuestionAnsweredEvent(
            caseId, currentPlayer.getId(), currentQuestion.getCategory(),
            currentQuestion.getValue(), answer, isCorrect, currentPlayer.getScore()
        );
        notifier.notifyObservers(event);
        
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