// model/GameEvent.java
package com.comp3607.model;

import java.time.LocalDateTime;

public abstract class GameEvent {
    protected String caseId;
    protected String playerId;
    protected String activity;
    protected LocalDateTime timestamp;
    
    public GameEvent(String caseId, String playerId, String activity) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters - ALL EXISTING CODE CAN STILL USE THESE
    public String getCaseId() { return caseId; }
    public String getPlayerId() { return playerId; }
    public String getActivity() { return activity; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    // Template Method Pattern for event formatting
    public abstract String getEventType();
    
    // Default implementations for backward compatibility
    public String getCategory() { return null; }
    public Integer getQuestionValue() { return null; }
    public String getAnswerGiven() { return null; }
    public String getResult() { return null; }
    public Integer getScoreAfterPlay() { return null; }
}