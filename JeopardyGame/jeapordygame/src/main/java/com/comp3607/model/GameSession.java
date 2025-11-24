package com.comp3607.model;

import java.util.*;

public class GameSession {
    private List<Player> players;
    private List<Question> questions;
    private int currentPlayerIndex;
    
    public GameSession(List<Player> players, List<Question> questions) {
        this.players = players;
        this.questions = questions;
        this.currentPlayerIndex = 0;
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public List<Player> getPlayers() { return players; }
    public List<Question> getQuestions() { return questions; }
}