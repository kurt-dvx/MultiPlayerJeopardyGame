package com.comp3607.model;

public class Player {
    private String id;
    private String name;
    private int score;
    
    // Default constructor
    public Player() {}
    
    // Constructor with id and name
    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }
    
    // Constructor with all fields
    public Player(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getScore() {
        return score;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    // Business logic methods
    public void addScore(int points) {
        int newScore = this.score + points;
        this.score = Math.max(0, newScore);
    }

    public void subtractScore(int points) {
        int newScore = this.score - points;
        this.score = Math.max(0, newScore);
    }
    
    public void resetScore() {
        this.score = 0;
    }
    
    // toString for debugging
    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Player player = (Player) o;
        return id.equals(player.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}