package com.comp3607.model;

import java.util.*;

public class GameSession {
    private List<Player> players;
    private List<Question> questions;
    private int currentPlayerIndex;
    private List<String> turnHistory;
    
    public GameSession(List<Player> players, List<Question> questions) {
        this.players = players;
        this.questions = questions;
        this.currentPlayerIndex = 0;
        this.turnHistory = new ArrayList<>();
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public List<Player> getPlayers() { 
        return new ArrayList<>(players); 
    }
    
    public List<Question> getQuestions() { 
        return new ArrayList<>(questions); 
    }
    
    // Turn history methods for report generation
    public void addTurnHistory(String turnInfo) {
        turnHistory.add(turnInfo);
    }
    
    public List<String> getTurnHistory() {
        return new ArrayList<>(turnHistory);
    }
    
    public int getTurnCount() {
        return turnHistory.size();
    }
}