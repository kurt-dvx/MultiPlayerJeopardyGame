package com.comp3607.model;

public class Player {
    private String id;
    private String name;
    private int score;
    
    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getScore() { return score; }
    
    // Business logic methods
    public void addScore(int points) {
        this.score += points;
    }

    public void subtractScore(int points) {
        this.score = Math.max(0, this.score - points);
    }
    
    @Override
    public String toString() {
        return name + " (" + id + "): $" + score;
    }
}